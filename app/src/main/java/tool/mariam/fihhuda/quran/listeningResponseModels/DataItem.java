package tool.mariam.fihhuda.quran.listeningResponseModels;

import com.google.gson.annotations.SerializedName;

public class DataItem {

    @SerializedName("pageNumber")
    private String pageNumber;

    @SerializedName("readerName")
    private String readerName;

    @SerializedName("link")
    private String link;

    @SerializedName("id")
    private String id;

    @SerializedName("type")
    private String type;

    @SerializedName("sora")
    private String sora;

    @SerializedName("soraNumber")
    private String soraNumber;

    @SerializedName("ayatsNumber")
    private String ayatsNumber;

    public String getPageNumber() {
        return pageNumber;
    }

    public String getReaderName() {
        return readerName;
    }

    public String getLink() {
        return link;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getSora() {
        return sora;
    }

    public String getSoraNumber() {
        return soraNumber;
    }

    public String getAyatsNumber() {
        return ayatsNumber;
    }

    @Override
    public String toString() {
        return
                "DataItem{" +
                        "pageNumber = '" + pageNumber + '\'' +
                        ",readerName = '" + readerName + '\'' +
                        ",link = '" + link + '\'' +
                        ",id = '" + id + '\'' +
                        ",type = '" + type + '\'' +
                        ",sora = '" + sora + '\'' +
                        ",soraNumber = '" + soraNumber + '\'' +
                        ",ayatsNumber = '" + ayatsNumber + '\'' +
                        "}";
    }
}