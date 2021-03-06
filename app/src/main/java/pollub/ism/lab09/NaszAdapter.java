package pollub.ism.lab09;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

// Klasa "NaszAdapter" musi dziedzyczyć po RecyclerView.Adapter, aby była adapterem
// RecyclerView.Adapter jest klasą szablonową
public class NaszAdapter extends RecyclerView.Adapter<NaszAdapter.NaszHolder> {

// ---
    // Klasa, która jest opisem danych jakie przyjmuje adapter
    public static class DaneAdaptera {
        public final  String nazwa, opis;
        public final Bitmap bitmapa;

        public DaneAdaptera(String nazwa, String opis, Bitmap bitmapa){
            this.nazwa = nazwa;
            this.opis = opis;
            this.bitmapa = bitmapa;
        }
    }
// ---


// ---
    // INTERFEJS implmenetuje inne klasy dostarczane do tego adaptera
    public interface DostawcaDanych{
        ArrayList<DaneAdaptera> getDane();
    }
// ---

// ---
    // KLASA WEWNETRZNA - ViewHolder przygotowany dla naszego adaptera (nasz holder)
    class NaszHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final NaszAdapter adapter;
        public final TextView nazwaLista;
        public final ImageView obrazLista;

        public NaszHolder(@NonNull View itemView, NaszAdapter adapter) {
            super(itemView);
            obrazLista = itemView.findViewById(R.id.obrazLista);
            nazwaLista = itemView.findViewById(R.id.nazwaLista);
            this.adapter = adapter;
            itemView.setOnClickListener(this);
        }

        // Implementacja kliknięcia myszką na element listy
        @Override
        public void onClick(View view) {
            DaneAdaptera danePozycji = dostawca.getDane().get(getLayoutPosition());
            Intent intent = new Intent(kontekst, AktywnoscOpis.class);
            intent.putExtra("nazwa", danePozycji.nazwa);
            intent.putExtra("opis", danePozycji.opis);

            //Osadzenie bitmampy w intencji
            ByteArrayOutputStream strumien = new ByteArrayOutputStream();
            danePozycji.bitmapa.compress(Bitmap.CompressFormat.PNG, 100, strumien);
            byte[] bity = strumien.toByteArray();       // Osadzenie bitmapy w intencji wymaga przesyłania bitów
            intent.putExtra("obraz",bity);
            kontekst.startActivity(intent);
        }

    }
// ---

// ---
    // KONTYNUACJA GLOWNEJ KLASY
    private final Context kontekst;
    private final DostawcaDanych dostawca;
    private LayoutInflater inflater;

    public NaszAdapter(Context kontekst, DostawcaDanych dostawca){
        this.kontekst = kontekst;
        this.inflater = LayoutInflater.from(kontekst);
        this.dostawca = dostawca;
    }


    @NonNull
    @Override
    public NaszHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View pozycja = inflater.inflate(R.layout.pozycja_recycler_view, parent, false);
        return new NaszHolder(pozycja,this);
    }

    @Override
    public void onBindViewHolder(@NonNull NaszHolder holder, int position) {
        DaneAdaptera dane = dostawca.getDane().get(position);
        holder.nazwaLista.setText(dane.nazwa);
        holder.obrazLista.setImageBitmap(dane.bitmapa);

    }

    @Override
    public int getItemCount() {
        return dostawca.getDane().size();
    }
// ---

}