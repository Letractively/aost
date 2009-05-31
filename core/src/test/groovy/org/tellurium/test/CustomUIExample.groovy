package org.tellurium.test

import org.tellurium.dsl.DslContext

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: May 27, 2009
 * 
 */

public class CustomUIExample extends DslContext {

  public void defineUi() {
    ui.Container(uid: "switch", clocator: [id: "switch1", tag: "switch"], namespace: "xforms", group: "true") {
      Container(uid: "case1", clocator: [id: "case-1", tag: "case"], namespace: "xforms")
      Container(uid: "case2", clocator: [id: "case-2", tag: "case"], namespace: "xforms")
      Container(uid: "case3", clocator: [id: "case-3", tag: "case"], namespace: "xforms")

    }

    ui.Container(uid:"caseRecordPopUp", clocator:[id:"CaseRecordsPopUp", tag:"div"],namespace:"xhtml",group:"true") {
      Container(uid:"date",clocator:[id:"caseRecordPopUpDate", tag:"input"], namespace:"xforms")
      Container(uid:"complaints",clocator:[id:"caseRecordPopUpComplaints", tag:"input"], namespace:"xforms")
      Container(uid:"weight",clocator:[id:"caseRecordPopUpWeight", tag:"input"], namespace:"xforms")
      Container(uid:"bp",clocator:[id:"caseRecordPopUpBP", tag:"input"], namespace:"xforms")
      Container(uid:"fh",clocator:[id:"caseRecordPopUpFH", tag:"input"], namespace:"xforms")
      Container(uid:"fhr",clocator:[id:"caseRecordPopUpFHR", tag:"input"], namespace:"xforms")
      Container(uid:"po",clocator:[id:"caseRecordPopUpPO", tag:"textarea"], namespace:"xforms")
      Container(uid:"pv",clocator:[id:"caseRecordPopUpPV", tag:"textarea"], namespace:"xforms")
      Container(uid:"remarks",clocator:[id:"caseRecordPopUpRemarks", tag:"textarea"], namespace:"xforms")

    }
  }

  public String getXCaseStatus(int tabnum){
      String caseId = "switch.case"+tabnum;
      String status = (String)customUiCall(caseId,"getXCaseStatus"); 
      return status;
  }

}