package tool.mariam.fihhuda.quran.adapters;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tool.mariam.fihhuda.R;
import tool.mariam.fihhuda.quran.fullQuranReadingModels.Ayah;

public class SurahDetailsAdapter
        extends RecyclerView.Adapter<SurahDetailsAdapter.SurahDetailsViewHolder> {

    List<List<Ayah>> allAyahs = new ArrayList<>();
    String surahName;
    List<Ayah> surahAyat = new ArrayList<>();
    int firstPage;
    int lastPage;

    public SurahDetailsAdapter(List<Ayah> surahAyat, String surahName) {
        this.surahAyat = surahAyat;
        this.surahName = surahName;
        firstPage = surahAyat.get(0).getPage();
        lastPage = surahAyat.get(surahAyat.size() - 1).getPage();
    }

    public static SpannableStringBuilder getSpannable(StringBuilder text) {

        SpannableStringBuilder spannable = new SpannableStringBuilder(text);

        String REGEX = "لل";
        Pattern p = Pattern.compile(REGEX);
        Matcher m = p.matcher(text);

        int start, end;

        //region allah match
        while (m.find()) {
            start = m.start();
            while (text.charAt(start) != ' ' && start != 0) {
                start--;
            }
            end = m.end();
            while (text.charAt(end) != ' ') {
                end++;
            }
            spannable.setSpan(new ForegroundColorSpan(Color.rgb(204, 0, 0)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        }
        //endregion

        return spannable;
    }

    public static SpannableStringBuilder getSpanOfText(SpannableStringBuilder text, String word) {
        SpannableStringBuilder spannable = new SpannableStringBuilder(text);
        String REGEX = word;
        Pattern p = Pattern.compile(REGEX);
        Matcher m = p.matcher(text);

        //    Log.d(TAG, text + "getSpanOfText: word " + word);
        while (m.find()) {
//            Log.d(TAG, "getSpanOfText: start " + m.start());
//            Log.d(TAG, "getSpanOfText: end " + m.end());
            spannable.setSpan(new ForegroundColorSpan(Color.rgb(51, 0, 0)), m.start(), m.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        }
        return spannable;

    }

    @NonNull
    @Override
    public SurahDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.surahdetails_item, parent, false);
        return new SurahDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SurahDetailsViewHolder holder, int position) {
        // List<Ayah> ayat = allAyahs.get(position);
        int firstPage = surahAyat.get(0).getPage();
        int lastPage = surahAyat.get(surahAyat.size() - 1).getPage();

        StringBuilder pageAyat = new StringBuilder(100000);

        for (int i = 0; i < surahAyat.size(); i++) {
            Ayah ayah = surahAyat.get(i);

            if ((ayah.getPage() - firstPage) == position) {
           /* SpannableStringBuilder spannedText = getSpannable(ayah.getText());
            String htmlString = Html.toHtml(spannedText);
            pageAyat.append(htmlString);*/
                pageAyat.append(ayah.getText());
                // String ramz="<font color=#b07a1a>(</font>";
                // String ayahNum = ramz+ayah.getNumberInSurah();
                // String ramz2="<font color=#b07a1a>)</font>";
                //  ayahNum=ayahNum+ramz2;
                pageAyat.append("(" + ayah.getNumberInSurah() + ")");
            }

        }

        SpannableStringBuilder s = getSpannable(pageAyat);
        SpannableStringBuilder s2 = getSpanOfText(s, "۞");
        // holder.ayaText.setText(Html.fromHtml(pageAyat.toString()),TextView.BufferType.SPANNABLE);
        holder.ayaText.setText(s2, TextView.BufferType.SPANNABLE);
        // holder.guzaNumberTextView.setText(ayat.get(0).getJuz()+"");
        // holder.pageNumberTextView.setText(ayat.get(0).getHizbQuarter()+"");
        holder.surahName.setText(this.surahName);
    }

    @Override
    public int getItemCount() {

        return (lastPage - firstPage) + 1;
    }

    class SurahDetailsViewHolder extends RecyclerView.ViewHolder {


        protected TextView ayaText;
        protected TextView guzaNumberTextView;
        protected TextView pageNumberTextView;
        protected TextView surahName;

        public SurahDetailsViewHolder(@NonNull View rootView) {
            super(rootView);
            ayaText = rootView.findViewById(R.id.ayaText);
            // pageNumberTextView = (TextView) rootView.findViewById(R.id.pageNumber_textView);
            // guzaNumberTextView = (TextView) rootView.findViewById(R.id.guzaNumber_textView);
            surahName = rootView.findViewById(R.id.surah_name);

        }
    }
}
