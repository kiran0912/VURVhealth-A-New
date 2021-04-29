package com.VURVhealth.vurvhealth.save;

/**
 * Created by yqlabs on 14/3/17.
 */

public class SaveItemDataObject {

    private String mText1;
    private String mText2;

    public SaveItemDataObject(String text1, String text2){

        mText1 = text1;
        mText2 = text2;

    }

    public String getmText1() {
        return mText1;
    }

    public void setmText1(String mText1) {
        this.mText1 = mText1;
    }

    public String getmText2() {
        return mText2;
    }

    public void setmText2(String mText2) {
        this.mText2 = mText2;
    }

}
