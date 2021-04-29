package com.VURVhealth.vurvhealth.prescriptions;

import java.util.List;

/**
 * Created by yqlabs on 31/1/17.
 */

public interface PrescriptionInterface {

    List<DrugSearchResultResPayLoad.Datum> getSearchResult();
}
