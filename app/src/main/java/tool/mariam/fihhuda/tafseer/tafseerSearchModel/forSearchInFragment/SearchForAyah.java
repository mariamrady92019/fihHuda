package tool.mariam.fihhuda.tafseer.tafseerSearchModel.forSearchInFragment;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchForAyah {

    @SerializedName("surahs")
    private List<SurahsSearchItem> surahs;

    public List<SurahsSearchItem> getSurahs() {
        return surahs;
    }

    @Override
    public String toString() {
        return
                "SearchForAyah{" +
                        "surahs = '" + surahs + '\'' +
                        "}";
    }
}