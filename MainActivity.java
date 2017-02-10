package com.example.user.impiccato;

/*
    Autore: Kunal Sahni
    Data : 06/02/2017
    Titolo: Impiccato
    Descirizione: applicazione per giocare all'impiccato, viene estratta una parola da un "dizionario" (in realtà è un vettore di Stringhe)
                  e l'utente deve indovinarla inserendola interamente o lettera per lettera.
                  **SOLO PER LA VERSIONE DI PROVA È POSSIBILE VEDERE LA PAROLA ESTRATTA TENENDO PREMUTO SUL TASTO**
*/

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    String parolaIndovino;                  //Stringa che contiene la parola da indovinare
    char[] stampa= new char[100] ;          //Array che contiene i caratteri da visualizzare nella TextView
    char[] vettIndovino = new char[100];    //Contiene la stringa da indovinare sottoforma di array di caratteri
    int indMaxStmp;                         //Contiene l'indice fino al quale viene utilizzato vettIndovino
    int numLettereRimanenti;                //Contatore dei caratteri '_' al posto delle lettere da indovinare
    int numTentativi;                       //Contiene il numero di tentativi rimanenti all'utente
    String lettereInserite="";              //Contiene tutte le lettere inserite

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText inserimento= (EditText) findViewById(R.id.editText);     //Riferimento all'EditText nell'app
        final Button button = (Button) findViewById(R.id.button);               //Riferimento al tasto nell'app
        button.setText("Giochiamo?");
        final TextView textTentativi = (TextView) findViewById(R.id.textView2);
        final ImageView immagine = (ImageView) findViewById(R.id.imageView);

        //Gestisco il click del tasto
        button.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        final EditText inserimento= (EditText) findViewById(R.id.editText);     //Riferimento all'EditText nell'app
                        final Button button = (Button) findViewById(R.id.button);               //Riferimento al tasto nell'app
                        //Controllo se l'utente sta già giocando o vuole cominciare a giocare
                        if (button.getText() == "Giochiamo?"){
                            reset();          //Resetto i widget quando l'utente preme il tasto per cominciare il gioco
                            setImage(numTentativi);
                        }else if(controllaStringa(inserimento) && numTentativi>0) {
                            final String parolaInserita = inserimento.getText().toString().toLowerCase();         //Inserisco nell'oggetto parolaInserita ciò che l'utente ha scritto nell'EditText
                            Toast toast = Toast.makeText(getApplicationContext(), "Non hai indovinato la parola, ritenta!", Toast.LENGTH_SHORT);
                            inserimento.setText("");
                            if (parolaInserita.length() > 1) {          //Controllo se l'utente ha inserito una stringa o un carattere
                                if (parolaInserita.compareTo(parolaIndovino) != 0) {
                                    toast.show();           //Mostra il toast che comunica all'utente di NON aver vinto
                                    numTentativi--;
                                } else {
                                    setText(true, '0');         //Richiamo la funzione setText in modo tale che vada a comunicare all'utente tramite la TextView la vittoria
                                }
                            } else {
                                int i;
                                char letter = parolaInserita.charAt(0);
                                if (lettereInserite.contains(parolaInserita)==false)
                                    setText(false, letter);         //Richiamo la funzione comunicando che l'utente non ha ancora vinto e che ha inserito la lettera letter
                                else{
                                    Toast t = Toast.makeText(getApplicationContext(), "Hai già inserito la lettera "+String.valueOf(letter), Toast.LENGTH_SHORT);
                                    t.show();
                                }
                                lettereInserite=lettereInserite.concat(String.valueOf(letter));
                            }
                            textTentativi.setText("Tentativi rimanenti: " + String.valueOf(numTentativi));
                            setImage(numTentativi);
                        }
                        else{
                            Toast t = Toast.makeText(getApplicationContext(), "Inserisci una lettera o la parola!", Toast.LENGTH_SHORT);
                            if(numTentativi<=0)
                                setText(false, '1');
                            else
                                t.show();
                        }
                    }
                }
        );

        //Gestisco il click del tasto invio da tastiera (equivale al tasto nell'app)
        inserimento.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                    if ((keyCode == KeyEvent.KEYCODE_DPAD_CENTER) || (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        final EditText inserimento= (EditText) findViewById(R.id.editText);     //Riferimento all'EditText nell'app
                        final Button button = (Button) findViewById(R.id.button);               //Riferimento al tasto nell'app
                        //Controllo se l'utente sta già giocando o vuole cominciare a giocare
                        if (button.getText() == "Giochiamo?"){
                            reset();          //Resetto i widget quando l'utente preme il tasto per cominciare il gioco
                            setImage(numTentativi);
                        }else if(controllaStringa(inserimento) && numTentativi>0) {
                            final String parolaInserita = inserimento.getText().toString().toLowerCase();         //Inserisco nell'oggetto parolaInserita ciò che l'utente ha scritto nell'EditText
                            Toast toast = Toast.makeText(getApplicationContext(), "Non hai indovinato la parola, ritenta!", Toast.LENGTH_SHORT);
                            inserimento.setText("");
                            if (parolaInserita.length() > 1) {          //Controllo se l'utente ha inserito una stringa o un carattere
                                if (parolaInserita.compareTo(parolaIndovino) != 0) {
                                    toast.show();           //Mostra il toast che comunica all'utente di NON aver vinto
                                    numTentativi--;
                                } else {
                                    setText(true, '0');         //Richiamo la funzione setText in modo tale che vada a comunicare all'utente tramite la TextView la vittoria
                                }
                            } else {
                                int i;
                                char letter = parolaInserita.charAt(0);
                                if (lettereInserite.contains(parolaInserita)==false)
                                    setText(false, letter);         //Richiamo la funzione comunicando che l'utente non ha ancora vinto e che ha inserito la lettera letter
                                else{
                                    Toast t = Toast.makeText(getApplicationContext(), "Hai già inserito la lettera "+String.valueOf(letter), Toast.LENGTH_SHORT);
                                    t.show();
                                }
                                lettereInserite=lettereInserite.concat(String.valueOf(letter));
                            }
                            textTentativi.setText("Tentativi rimanenti: " + String.valueOf(numTentativi));
                            setImage(numTentativi);
                        }
                        else{
                            Toast t = Toast.makeText(getApplicationContext(), "Inserisci una lettera o la parola!", Toast.LENGTH_SHORT);
                            if(numTentativi<=0)
                                setText(false, '1');
                            else
                                t.show();
                        }
                        return true;
                    }
                return false;
            }
        });

        //**SOLO PER TESTARE L'APP** (mostra la parola da indovinare)
        button.setOnLongClickListener(
                new View.OnLongClickListener(){
                    public boolean onLongClick(View v){
                        Toast t = Toast.makeText(getApplicationContext(), parolaIndovino, Toast.LENGTH_SHORT);
                        t.show();
                        return true;
                    }
                }
        );

    }



    //resetta i widget ed estrae la parola da indovinare
    public void reset(){
        final Resources res = getResources();
        final Random r = new Random();
        final int numRand= r.nextInt(40000);                                  //Estraggo un numero casuale che sarà l'indice della parola da indovinare
        parolaIndovino = res.getStringArray(R.array.dizionario)[numRand];     //Inserisco la parola da indovinare nell'oggetto stringa parolaIndovino
        lettereInserite="";
        final ImageView immagine = (ImageView) findViewById(R.id.imageView);
        immagine.setImageResource(R.drawable.img00);
        TextView textTentativi = (TextView) findViewById(R.id.textView2);
        numTentativi=7;
        numLettereRimanenti=0;                                        //Azzero il contatore del numero delle lettere da indovinare
        EditText inserimento= (EditText) findViewById(R.id.editText);   //Riferimento all'EditText nell'app
        Button button = (Button) findViewById(R.id.button);             //Riferimento al tasto nell'app
        button.setText("L'accendiamo?");
        int i;
        inserimento.setText("");                                        //Cancello tutto quel che c'era nell'oggetto inserimento
        vettIndovino=parolaIndovino.toCharArray();                      //Inserisco nell'array la stringa come sequenza di caratteri
        textTentativi.setText("Tentativi rimanenti: "+String.valueOf(numTentativi));
        setText(false, '0');                                            //Richiama la funzione che cambia il testo della TextView nell'app
    }




    /*
    Se indovinato=1 comunica la vittoria
    Se indovinato=0 e letter='0' resetto l'array stampa
    Se indovinato=0 e letter !='0' visualizzo nella TextView la lettera inserita dall'utente (se è presente)
    Se indovinato=0 e letter='0' comunica la perdita
    */
    public void setText(boolean indovinato, char letter) {
        int i;
        TextView label = (TextView) findViewById(R.id.textView);

        if (!indovinato && letter == '0') {

            parolaIndovino.getChars(0, 1, stampa, 0);               //Inserisco nell'array stampa il primo carattere della stringa da indovinare
            for (i = 1; i < parolaIndovino.length() * 2; i++) {
                if (i % 2 == 0){
                    stampa[i] = '_';
                    numLettereRimanenti++;
                }
                else
                    stampa[i] = ' ';
            }
            parolaIndovino.getChars(parolaIndovino.length() - 1, parolaIndovino.length(), stampa, i-2);     //Inserisco nell'array stampa l'ultimo carattere della stringa da indovinare
            indMaxStmp=i-1;
            label.setText(stampa, 0, indMaxStmp);

        }else if (!indovinato && letter != '0' && letter!='1'){
            boolean letteraTrovata=false;
            for(i=1; i<parolaIndovino.length()-1; i++)
                if(letter==vettIndovino[i]) {
                    stampa[i * 2] = letter;
                    numLettereRimanenti--;
                    letteraTrovata=true;
                }
            if(numLettereRimanenti==1) {
                setText(true, '0');
                label.setText("Hai indovinato la parola!");
            }
            if(letteraTrovata)
                label.setText(stampa, 0, indMaxStmp);
            else{
                Toast t = Toast.makeText(getApplicationContext(), "La lettera inserita non è presente, ritenta!", Toast.LENGTH_SHORT);
                t.show();
                numTentativi--;
            }
            if(numTentativi==0)
                setText(false,'1');
        }else if(!indovinato && letter=='1'){

            Button button = (Button) findViewById(R.id.button);
            TextView textTentativi = (TextView) findViewById(R.id.textView2);
            Toast t = Toast.makeText(getApplicationContext(), "Hai finito i tentativi disponibili!", Toast.LENGTH_SHORT);

            setImage(0);
            t.show();
            textTentativi.setText("");
            label.setText("Hai perso!");
            button.setText("Giochiamo?");
            EditText inserimento= (EditText) findViewById(R.id.editText);
            inserimento.setText("Per giocare premi il tasto");

        }else if(indovinato){

            Button button = (Button) findViewById(R.id.button);
            TextView textTentativi = (TextView) findViewById(R.id.textView2);
            Toast t = Toast.makeText(getApplicationContext(), "Hai indovinato la parola!", Toast.LENGTH_SHORT);

            setImage(-1);
            t.show();
            textTentativi.setText("");
            label.setText("Hai indovinato la parola!");
            button.setText("Giochiamo?");
            EditText inserimento= (EditText) findViewById(R.id.editText);
            inserimento.setText("Per giocare premi il tasto");

        }
    }




    //Controlla se la stringa inserita è valida
    public boolean controllaStringa(EditText inserimento){
        String text = String.valueOf(inserimento.getText());
        if(text.isEmpty())
            return false;
        char [] textArray = text.toCharArray();
        int i;
        for(i=0; i<textArray.length; i++)
            if(!Character.isLetter(textArray[i]))
                return false;
        return true;
    }


    public void setImage(int numTentativi){
        final ImageView immagine = (ImageView) findViewById(R.id.imageView);
        switch (numTentativi){
            case 0:
                immagine.setImageResource(R.drawable.img08);
                break;
            case 1:
                immagine.setImageResource(R.drawable.img07);
                break;
            case 2:
                immagine.setImageResource(R.drawable.img06);
                break;
            case 3:
                immagine.setImageResource(R.drawable.img05);
                break;
            case 4:
                immagine.setImageResource(R.drawable.img04);
                break;
            case 5:
                immagine.setImageResource(R.drawable.img03);
                break;
            case 6:
                immagine.setImageResource(R.drawable.img02);
                break;
            case 7:
                immagine.setImageResource(R.drawable.img01);
                break;
            case -1:
                immagine.setImageResource(R.drawable.img00);
                break;
        }
    }
}