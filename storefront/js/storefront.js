        jQuery.datetimepicker.setLocale('de');

        // Server Adresse
        var serverURL = 'http://192.168.99.100:9000/';
    
        // Handlebar Stuff
        Handlebars.registerHelper("xif", function (v1, v2, options) {
            return v1 === v2 ? options.fn(this) : options.inverse(this);
        });
    
        // Lade Templates
        var menu_template =             Handlebars.compile( $("#menu-template").html() );
        var opslots_template =          Handlebars.compile( $("#opslots-template").html() );
        var reservations_template =     Handlebars.compile( $("#reservations-template").html() );
        var notification_template =     Handlebars.compile( $("#notification-template").html() );
        var newOpSlot_template =        Handlebars.compile( $("#newOpSlot-template").html() );
        var newReservation_template =   Handlebars.compile( $("#newReservation-template").html() );
        var userSelection_template =    Handlebars.compile( $("#userSelection-template").html() );
        var status_template =           Handlebars.compile( $("#status-template").html() );
    

    
        // Shorthand for $( document ).ready()
        $(function() {
            
            // Change Usergroup to Public
            $('#usergroup').val('public').change();
            
        });
    
        // ---------------------------------------------------------------------------------------------
    
        /**
         *  Wenn die Usergruppe gewählt wird, lade die Benutzer und das Menü
         *
         **/
        $('#usergroup').change(function() {
        
            var option = $(this).find('option:selected').val();
            
            $('#user-selection').html('Benutzer werden geladen ...');
            $('#main-menu').html('Menü wird geladen ...');
            
            // Build Menu with selected Usergroup
            $('#main-menu').html( menu_template({
                usergroup: option,
            }));
            
            // Lade die öffentliche Liste
            $('a.menu-publicSlotlist').click();
            
            if( option == 'public' ) {
            
                // Build User Selection
                $('#user-selection').html('');
                
                // Build Menu with selected Usergroup
                $('#main-menu').html( menu_template({
                    usergroup: 'public',
                }));
            
            } else {
            
                // getUsers (doctor,patient,hospital)
                $.ajax({
                    method: "GET",
                    url: serverURL+'api/masterdata/'+option,
                    data: { },
                    cache: false,
                }).done(function( data ) {
                    
                    // Build User Selection
                    $('#user-selection').html( userSelection_template({
                        user: data,
                    }));
                    
                }).fail(function() {

                    $('#user-selection').html('Fehler beim Laden von Benutzern!');
                    
                }).always(function( data, textStatus, jqXHR ) {
                    console.log(data);
                    console.log(textStatus);
                    console.log(jqXHR);
                });
            
            }
            
        });
        
        // --------------------------------------------------------------------------------------------------------------
        
        /**
         *  Lade Liste mit öffentlochen OP-Slots und deren Reservierungen
         *
         **/
        $(document).on('click', 'a.menu-publicSlotlist', function() {
    
            $('#main-window').html('Ansicht wird geladen ...');
    
            $.ajax({
                method: "GET",
                url: serverURL+'api/booking/findAll',
                data: { },
                cache: false,
            }).done(function( dataSet ) {
                
                $('#main-window').html( opslots_template({'delete':0, 'patient':0, 'hospital':1, 'doctor':1, 'status':1}));
                
                $('table.opslots-table').DataTable( {
                    data: dataSet,
                    columns: [ 
                        {data: 'type'},
                        {data: 'doctorName'},
                        //{data: 'patientName'},
                        {data: 'hospitalName'},
                        {
                            data: 'startTime',
                            'render': function (data) {
                                return $.format.date(data, "dd.MM HH:mm");
                            }
                        },{
                            data: 'endTime',
                            'render': function (data) {
                                return $.format.date(data, "dd.MM HH:mm");
                            }
                        },
                        {data: 'status'},
                    ],
                    'order': [[ 3, "asc" ]],
                    initComplete: function () {
                        this.api().columns().every( function () {
                            var column = this;
                            var select = $('<select><option value=""></option></select>')
                                .appendTo( $(column.footer()).empty() )
                                .on( 'change', function () {
                                    var val = $.fn.dataTable.util.escapeRegex(
                                        $(this).val()
                                    );
             
                                    column
                                        .search( val ? '^'+val+'$' : '', true, false )
                                        .draw();
                                } );
             
                            column.data().unique().sort().each( function ( d, j ) {
                                if( d == null ) return;
                                select.append( '<option value="'+d+'">'+d+'</option>' )
                            } );
                        } );
                    }
                } );
                
            }).fail(function() {
                
                $('#main-window').html('Ansicht konnte nicht geladen werden!');
                
            }).always(function( data, textStatus, jqXHR ) {
                console.log(data);
                console.log(textStatus);
                console.log(jqXHR);
            });
            
            return false;
    
    
            
/*    
            $('table.opslots-table').DataTable( {
                data: dataSet,
                initComplete: function () {
                    this.api().columns().every( function () {
                        var column = this;
                        var select = $('<select><option value=""></option></select>')
                            .appendTo( $(column.footer()).empty() )
                            .on( 'change', function () {
                                var val = $.fn.dataTable.util.escapeRegex(
                                    $(this).val()
                                );
         
                                column
                                    .search( val ? '^'+val+'$' : '', true, false )
                                    .draw();
                            } );
         
                        column.data().unique().sort().each( function ( d, j ) {
                            select.append( '<option value="'+d+'">'+d+'</option>' )
                        } );
                    } );
                }
            } );

            return false;
*/
        });
        
        /**
         *  Lade Liste mit Notification für den aktuellen Benutzer
         *
         **/
        $(document).on('click', 'a.menu-notifications', function() {
    
            $('#main-window').html('Ansicht wird geladen ...');
    
            var option = $('#usergroup').find('option:selected').val();
            var userID = $('#user-selection').find('option:selected').val();
    
            $.ajax({
                method: "GET",
                url: serverURL+'api/ringme/findNotification/'+option+'/'+userID,
                data: { },
                cache: false,
            }).done(function( dataSet ) {
                
                $('#main-window').html( notification_template({}));
                
                $('table.notification-table').DataTable( {
                    data: dataSet,
                    columns: [{
                        'data': 'date', 
                        'render': function (data) {
                            return $.format.date(data, "dd.MM HH:mm");
                        }
                    },
                        {data: 'text'} 
                    ],
                    'order': [[ 0, "asc" ]],
                } );
                
            }).fail(function() {
                
                $('#main-window').html('Ansicht konnte nicht geladen werden!');
                
            }).always(function( data, textStatus, jqXHR ) {
                console.log(data);
                console.log(textStatus);
                console.log(jqXHR);
            });
            
            return false;
        
        });
        
        /**
         *  Lade das Formular zum anlegen eines neuen OP Slots
         *
         **/
        $(document).on('click', 'a.menu-newOPslot', function() {
        
            $('#main-window').html('Ansicht wird geladen ...');
        
            $('#main-window').html( newOpSlot_template({}));
            
            $('input.datetimepicker').datetimepicker();;
        
            return false;
        
        });
        
        /**
         *  Absenden des Fomrulars für einen neuen OP-Slot
         *
         **/
        $(document).on('submit', 'form.form-newOpSlot', function() {
        
            var userID = $('#user-selection').find('option:selected').val();
        
            var startTime = (new Date($('#startTime').val()));
            var endTime = (new Date( startTime.getTime() +  $('#length').val() * 60000 ));
        
            $.ajax({
                method: "POST",
                url: serverURL+'/api/booking/create',
                headers: { 
                    'Accept': 'application/json',
                    'Content-Type': 'application/json' 
                },
                data: JSON.stringify({ 
                    'type': $('#type').val(), 
                    'startTime': startTime.toISOString(), 
                    'endTime': endTime.toISOString(), 
                    'status': 'AVAILABLE',
                    'hospitalId': userID,
                }),
                cache: false,
            }).done(function( data ) {
                alert( "OP-Slot erfolgreich angelegt" );  
                $('a.menu-hospitalOPslots').click();
            }).fail(function() {
                alert( "Fehler beim anlegen des OP-Slots." );
            }).always(function( data, textStatus, jqXHR ) {
                console.log(data);
                console.log(textStatus);
                console.log(jqXHR);
            });
        
            return false;
        
        });
        
        /**
         *  Lade Formular für neue Reservierung
         *
         **/
        $(document).on('click', 'a.menu-newReservation', function() {
        
            $('#main-window').html('Ansicht wird geladen ...');
        
            // getUsers (doctor,patient,hospital)
            $.ajax({
                method: "GET",
                url: serverURL+'api/masterdata/patient',
                data: { },
                cache: false,
            }).done(function( data ) {
                
                $('#main-window').html( newReservation_template({
                    user: data,                    
                }));
                
                $('input.datetimepicker').datetimepicker();;
                
            }).fail(function() {

                $('#main-window').html('Fehler beim Laden von Benutzern!');
                
            }).always(function( data, textStatus, jqXHR ) {
                console.log(data);
                console.log(textStatus);
                console.log(jqXHR);
            });
            
            return false;
        
        });
        

        /**
         *  Absenden des Reservierungsformulars
         *
         **/
        $(document).on('submit', 'form.form-newReservation', function() {
        
            var userID = $('#user-selection').find('option:selected').val();
        
            $.ajax({
                method: "POST",
                url: serverURL+'/api/opscanner/findAndBook',
                headers: { 
                    'Accept': 'application/json',
                    'Content-Type': 'application/json' 
                },
                data: JSON.stringify({ 
                    'patientId': $('#patient').val(), 
                    'doctorId': userID, 
                    'begin': (new Date($('#begin').val()).toISOString()), 
                    'end': (new Date($('#end').val()).toISOString()), 
                    'duration': $('#duration').val() * 60000, 
                    'type': $('#type').val(), 
                }),
                cache: false,
            }).done(function( data ) {
                alert( "Reservierung erfolgreich durchgeführt." );  
                $('a.menu-doctorOPslots').click(); 
            }).fail(function() {
                alert( "Felhler beim anlegen der Reservierung." );
            }).always(function( data, textStatus, jqXHR ) {
                console.log(data);
                console.log(textStatus);
                console.log(jqXHR);
            });
        
            return false;
        
        });
        
        
        /**
         *  Lade Liste mit Reservierungen für den Patienten
         *
         **/
        $(document).on('click', 'a.menu-reservations', function() {
        
            $('#main-window').html('Ansicht wird geladen ...');
            
            var option = $('#usergroup').find('option:selected').val(); 
            var userID = $('#user-selection').find('option:selected').val();
            
            $.ajax({
                method: "GET",
                url: serverURL+'api/booking/findOPSlots/'+option+'/'+userID,
                data: { },
                cache: false,
            }).done(function( dataSet ) {
                
                $('#main-window').html( opslots_template({'delete':0, 'patient':0, 'hospital':1, 'doctor':1, 'status':0 }));
                
                $('table.opslots-table').DataTable( {
                    data: dataSet,
                    columns: [ 
                        {data: 'type'},
                        {data: 'doctorName'},
                        //{data: 'patientName'},
                        {data: 'hospitalName'},
                        {
                            data: 'startTime',
                            'render': function (data) {
                                return $.format.date(data, "dd.MM HH:mm");
                            }
                        },{
                            data: 'endTime',
                            'render': function (data) {
                                return $.format.date(data, "dd.MM HH:mm");
                            }
                        },
                        //{data: 'status'},
                    ],
                    'order': [[ 3, "asc" ]],
                    initComplete: function () {
                        this.api().columns().every( function () {
                            var column = this;
                            var select = $('<select><option value=""></option></select>')
                                .appendTo( $(column.footer()).empty() )
                                .on( 'change', function () {
                                    var val = $.fn.dataTable.util.escapeRegex(
                                        $(this).val()
                                    );
             
                                    column
                                        .search( val ? '^'+val+'$' : '', true, false )
                                        .draw();
                                } );
             
                            column.data().unique().sort().each( function ( d, j ) {
                                if( d == null ) return;
                                select.append( '<option value="'+d+'">'+d+'</option>' )
                            } );
                        } );
                    }
                } );
                
            }).fail(function() {
                
                $('#main-window').html('Ansicht konnte nicht geladen werden!');
                
            }).always(function( data, textStatus, jqXHR ) {
                console.log(data);
                console.log(textStatus);
                console.log(jqXHR);
            });
            
            return false;
        
        });
        
        /**
         *  Lade Liste von OP Slots diesen Arzt
         *
         **/
        $(document).on('click', 'a.menu-doctorOPslots', function() {
        
            $('#main-window').html('Ansicht wird geladen ...');
            
            var option = $('#usergroup').find('option:selected').val(); 
            var userID = $('#user-selection').find('option:selected').val();
            
            $.ajax({
                method: "GET",
                url: serverURL+'api/booking/findOPSlots/'+option+'/'+userID,
                data: { },
                cache: false,
            }).done(function( dataSet ) {
                
                $('#main-window').html( opslots_template({'delete':1, 'patient':1, 'hospital':1, 'doctor':0, 'status':0}));
                
                $('table.opslots-table').DataTable( {
                    data: dataSet,
                    columns: [ 
                        {data: 'type'},
                        //{data: 'doctorName'},
                        {data: 'patientName'},
                        {data: 'hospitalName'},
                        {
                            data: 'startTime',
                            'render': function (data) {
                                return $.format.date(data, "dd.MM HH:mm");
                            }
                        },{
                            data: 'endTime',
                            'render': function (data) {
                                return $.format.date(data, "dd.MM HH:mm");
                            }
                        },
                        //{data: 'status'},
                        {   data: 'id',
                            'render': function (data) {
                                return '<button class="deleteReservation" type="button" value="'+data+'">Stornieren</button>';
                            }
                        },
                    ],
                    'order': [[ 3, "asc" ]],
                    initComplete: function () {
                        this.api().columns().every( function () {
                            var column = this;
                            var select = $('<select><option value=""></option></select>')
                                .appendTo( $(column.footer()).empty() )
                                .on( 'change', function () {
                                    var val = $.fn.dataTable.util.escapeRegex(
                                        $(this).val()
                                    );
             
                                    column
                                        .search( val ? '^'+val+'$' : '', true, false )
                                        .draw();
                                } );
             
                            column.data().unique().sort().each( function ( d, j ) {
                                if( d == null ) return;
                                select.append( '<option value="'+d+'">'+d+'</option>' )
                            } );
                        } );
                    }
                } );
                
            }).fail(function() {
                
                $('#main-window').html('Ansicht konnte nicht geladen werden!');
                
            }).always(function( data, textStatus, jqXHR ) {
                console.log(data);
                console.log(textStatus);
                console.log(jqXHR);
            });
            
            return false;
        
        });
        
        /**
         *      Löschen einer Reservierung
         *
         **/
        $(document).on('click', 'button.deleteReservation', function() {
        
            var id = $(this).attr("value");
        
            $.ajax({
                method: "PUT",
                url: serverURL+'api/booking/cancelReservation/'+id,
                data: { },
                cache: false,
            }).done(function( data ) {
                alert( "Reservierung storniert." );  
                $('a.menu-doctorOPslots').click();
            }).fail(function() {
                alert( "Fehler beim stornieren der Reervierung." );
            }).always(function( data, textStatus, jqXHR ) {
                console.log(data);
                console.log(textStatus);
                console.log(jqXHR);
            });
        
        });
        
        /** 
         *      Lade Liste von OP Slots dieses Krankenhauses
         *
         **/
        $(document).on('click', 'a.menu-hospitalOPslots', function() {
        
            $('#main-window').html('Ansicht wird geladen ...');
            
            var option = $('#usergroup').find('option:selected').val(); 
            var userID = $('#user-selection').find('option:selected').val();
            
            $.ajax({
                method: "GET",
                url: serverURL+'api/booking/findOPSlots/'+option+'/'+userID,
                data: { },
                cache: false,
            }).done(function( dataSet ) {
                
                $('#main-window').html( opslots_template({'delete':1, 'patient':1, 'hospital':0, 'doctor':1, 'status':1}));
                
                $('table.opslots-table').DataTable( {
                    data: dataSet,
                    columns: [ 
                        {data: 'type'},
                        {data: 'doctorName'},
                        {data: 'patientName'},
                        //{data: 'hospitalName'},
                        {
                            data: 'startTime',
                            'render': function (data) {
                                return $.format.date(data, "dd.MM HH:mm");
                            }
                        },{
                            data: 'endTime',
                            'render': function (data) {
                                return $.format.date(data, "dd.MM HH:mm");
                            }
                        },
                        {data: 'status'},{
                            data: 'id',
                            'render': function (data) {
                                return '<button class="deleteOPslots" type="button" value="'+data+'">Löschen</button>';
                            }
                        },
                    ],
                    'order': [[ 3, "asc" ]],
                    initComplete: function () {
                        this.api().columns().every( function () {
                            var column = this;
                            var select = $('<select><option value=""></option></select>')
                                .appendTo( $(column.footer()).empty() )
                                .on( 'change', function () {
                                    var val = $.fn.dataTable.util.escapeRegex(
                                        $(this).val()
                                    );
             
                                    column
                                        .search( val ? '^'+val+'$' : '', true, false )
                                        .draw();
                                } );
             
                            column.data().unique().sort().each( function ( d, j ) {
                                if( d == null ) return;
                                select.append( '<option value="'+d+'">'+d+'</option>' )
                            } );
                        } );
                    }
                } );
                
            }).fail(function() {
                
                $('#main-window').html('Ansicht konnte nicht geladen werden!');
                
            }).always(function( data, textStatus, jqXHR ) {
                console.log(data);
                console.log(textStatus);
                console.log(jqXHR);
            });
            
            return false;
        
        });
        
        /**
         *      Löschen eines OP-Slots
         *
         **/
        $(document).on('click', 'button.deleteOPslots', function() {
        
            var id = $(this).attr("value");
        
            $.ajax({
                method: "DELETE",
                url: serverURL+'api/booking/deleteReservation/'+id,
                data: { },
                cache: false,
            }).done(function( data ) {
                alert( "OP Slot gelöscht." ); 
                $('a.menu-hospitalOPslots').click();                
            }).fail(function() {
                alert( "Fehler beim löschen des OP Slots." );
            }).always(function( data, textStatus, jqXHR ) {
                console.log(data);
                console.log(textStatus);
                console.log(jqXHR);
            });
        
        });
       
        /**
         *  Statuwerte laden
         *
         **/
        $(document).on('click', 'a.menu-status', function() {
        
            $('#main-window').html('Ansicht wird geladen ...');
        
            $.ajax({
                method: "GET",
                url: serverURL+'api/status',
                data: { },
                cache: false,
            }).done(function( data ) {
                
                $('#main-window').html( status_template({ 'status': data }) );
                
            }).fail(function() {
                
                $('#main-window').html('Ansicht konnte nicht geladen werden!');
                
            }).always(function( data, textStatus, jqXHR ) {
                console.log(data);
                console.log(textStatus);
                console.log(jqXHR);
            });
        
            
        
            return false;
        
        });

        
        // --------------------------------------------------------------------------
        
        // DEBUG: Hinzufügen von Testdaten
        $(document).on('click', 'button#addTestData', function() {
    
            $.ajax({
                method: "POST",
                url: serverURL+'test/'+'addTestData',
                data: { },
                cache: false,
            }).done(function( data ) {
                alert( "TestDaten erzeugt." ); 
                location.reload();
            }).fail(function() {
                alert( "Fehler beim erzeúgen der Testdaten." );
            }).always(function( data, textStatus, jqXHR ) {
                console.log(data);
                console.log(textStatus);
                console.log(jqXHR);
            });
        
        });
        
        // DEBUG: Löschen aller Daten aus dern DB
        $(document).on('click', 'button#deleteAll', function() {
    
            $.ajax({
                method: "DELETE",
                url: serverURL+'test/'+'deleteAll',
                data: { },
                cache: false,
            }).done(function( data ) {
                alert( "Alle Daten gelöscht." ); 
                location.reload();
            }).fail(function() {
                alert( "Fehler beim löschen der Daten." );
            }).always(function( data, textStatus, jqXHR ) {
                console.log(data);
                console.log(textStatus);
                console.log(jqXHR);
            });
        
        });
        
        // DEBUG: Erzeugen einer Notification für den aktuellen User
        $(document).on('click', 'button#addNotification', function() {
    
            var option = $('#usergroup').find('option:selected').val();
            var userID = $('#user-selection').find('option:selected').val();
    
            $.ajax({
                method: "POST",
                url: serverURL+'test/addNotification',
                headers: { 
                    'Accept': 'application/json',
                    'Content-Type': 'application/json' 
                },
                dataType: 'json',
                data: JSON.stringify({
                    entityId: userID,
                    type: option.toUpperCase(),
                    date: (new Date().toISOString()),
                    text: 'testEintrag',
                }),
                cache: false,
            }).done(function( data ) {
                alert( "Notification erzeugt." );  
            }).fail(function() {
                alert( "Fehler beim erzeugen einer Notification." );
            }).always(function( data, textStatus, jqXHR ) {
                console.log(data);
                console.log(textStatus);
                console.log(jqXHR);
            });
        
        });

      