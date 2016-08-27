package dse.ss2016.group16.surgeryplanner.apigateway.service;

import dse.ss2016.group16.surgeryplanner.core.dto.OPScannerRequestWrapper;

/**
 * Created by Manuel Roblek (mroblek@tuwien.ac.at) on 13.06.2016.
 */
public interface IOPScannerApiController {


    String findAndBookFreeSlot(OPScannerRequestWrapper request);
}
