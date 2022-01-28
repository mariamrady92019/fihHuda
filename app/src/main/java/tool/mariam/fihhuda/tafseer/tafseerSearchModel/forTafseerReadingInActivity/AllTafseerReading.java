package tool.mariam.fihhuda.tafseer.tafseerSearchModel.forTafseerReadingInActivity;

import com.google.gson.annotations.SerializedName;

public class AllTafseerReading {

    @SerializedName("code")
    private int code;

    @SerializedName("data")
    private Data data;

    @SerializedName("status")
    private String status;

    public int getCode() {
        return code;
    }

    public Data getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return
                "AllTafseerReading{" +
                        "code = '" + code + '\'' +
                        ",data = '" + data + '\'' +
                        ",status = '" + status + '\'' +
                        "}";
    }
}