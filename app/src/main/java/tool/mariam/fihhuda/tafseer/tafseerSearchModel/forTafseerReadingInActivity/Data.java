package tool.mariam.fihhuda.tafseer.tafseerSearchModel.forTafseerReadingInActivity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @SerializedName("edition")
    private Edition edition;

    @SerializedName("surahs")
    private List<SurahsItem> surahs;

    public Edition getEdition() {
        return edition;
    }

    public List<SurahsItem> getSurahs() {
        return surahs;
    }

    @Override
    public String toString() {
        return
                "Data{" +
                        "edition = '" + edition + '\'' +
                        ",surahs = '" + surahs + '\'' +
                        "}";
    }
}