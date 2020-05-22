package fr.willy.linky;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothClass;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import static android.view.View.GONE;
import static android.widget.Toast.makeText;

public class CustomPopUp extends Dialog {
    /*
    Cette classe permet de générer un popup personnalisé.
    Elle est associée à un layout spécifique nommé popup_device_adding.
     */

    //fields
    private String title;
    private String subtitle;
    private TextView confirm_text, cancel_text; //bouton "confirmer" et "annuler" du popup
    private TextView titleView, subtitleView;
    private Spinner device_spinner;
    private DeviceActivity parent_activity;
    private Button button_test_config;

    //constructor
    public CustomPopUp(DeviceActivity deviceActivity, String popup)
    {


        super(deviceActivity, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        //on charge notre layout associé au popup
        if (popup.equals("adding")){
            setContentView(R.layout.popup_device_adding);
        }
        else {
            setContentView(R.layout.popup_device_configuration);
        }

        //empeche l'utilisateur de fermer le popup en appuyant à
        //l'exterieur de celui-ci.
        this.setCancelable(false);
        this.confirm_text = findViewById(R.id.confirm_text);
        this.cancel_text  = findViewById(R.id.cancel_text);
        this.titleView    = findViewById(R.id.device_popup_title);
        this.subtitleView = findViewById(R.id.device_popup_subtitle);
        this.device_spinner = findViewById(R.id.device_spinner);
        this.parent_activity = deviceActivity;
    }

    //changer le titre du popup
    public void setTitle(String title){this.title = title;}

    //changer le sous titre du popup
    public void setSubtitle(String subtitle){this.subtitle = subtitle;}

    //recupere l'appareil selectionné dans le spinner (menu déroulant)
    public String getSpinnerData(){
        final String s = this.device_spinner.getSelectedItem().toString();
        return s;
        //this.device_spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this.parent_activity);
    }

    //recupere le champ "confirmer" du popup
    public TextView getConfirm_text(){return confirm_text;}

    //recupere le champ "annuler" du popup
    public TextView getCancel_text(){return cancel_text;}

    public Button getButton_test_config(){return button_test_config;}

    //affiche le popup
    public void test_bluid(){
        show();
    }

    //gérer configure
    public void configuration_protocol(String device, final CustomPopUp popUp, final String ip_for_sending)
    {

        if(!device.equals(""))
        {
            TextView deviceTextView = findViewById(R.id.textView);
            final TextView protocolTextView = findViewById(R.id.textView3);
            final Button faitButton = findViewById(R.id.button);
            final Button ajouterButton = findViewById(R.id.button2);
            final CheckBox  debranchable = findViewById(R.id.checkBox);

            final int[] pappUnplugged = new int[1];
            final int[] pappOn = new int[1];
            final int[] pappOff = new int[1];
            final int[] diffPappOnOff = new int[1];
            final int[] diffPappUnpluggedOff = new int[1];

            final int[] counter = {6};

            ajouterButton.setVisibility(GONE);
            faitButton.setVisibility(View.VISIBLE);
            debranchable.setVisibility(View.VISIBLE);

            faitButton.setText("OK");
            deviceTextView.setText(device);
            protocolTextView.setText("Gna gna gna explication + éteint ton appareil");


            faitButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    faitButton.setVisibility(GONE);
                    debranchable.setVisibility(GONE);
                    MainActivity.ask_tele_info(ip_for_sending,10001);

                    if(debranchable.isChecked() == true)
                    {
                        new CountDownTimer(5000, 1000){
                            public void onTick(long millisUntilFinished){
                                protocolTextView.setText("DEBRANCHE TON APPAREIL !\n"+String.valueOf(counter[0])+" secondes");
                                counter[0]--;
                                protocolTextView.setText("DEBRANCHE TON APPAREIL !\n"+String.valueOf(counter[0])+" secondes");
                            }
                            public  void onFinish(){
                                protocolTextView.setText("DEBRANCHE TON APPAREIL !\n0 secondes");
                                faitButton.setVisibility(View.VISIBLE);
                            }
                        }.start();


                        faitButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pappUnplugged[0] = Integer.parseInt(MainActivity.papp);
                                protocolTextView.setText("BRANCHE ET ALLUME TON APPAREIL ! \n"+String.valueOf(counter[0])+" secondes\npappUnplugged = "+pappUnplugged[0]);

                                counter[0] = 6;
                                faitButton.setVisibility(GONE);

                                new CountDownTimer(5000, 1000){
                                    public void onTick(long millisUntilFinished){
                                        protocolTextView.setText("BRANCHE ET ALLUME TON APPAREIL ! \n"+String.valueOf(counter[0])+" secondes\npappUnplugged = "+pappUnplugged[0]);
                                        counter[0]--;
                                        protocolTextView.setText("BRANCHE ET ALLUME TON APPAREIL ! \n"+String.valueOf(counter[0])+" secondes\npappUnplugged = "+pappUnplugged[0]);
                                    }
                                    public  void onFinish(){
                                        protocolTextView.setText("BRANCHE ET ALLUME TON APPAREIL ! \n0 secondes\npappUnplugged = "+pappUnplugged[0]);
                                        faitButton.setVisibility(View.VISIBLE);
                                    }
                                }.start();


                                faitButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        pappOn[0] = Integer.parseInt(MainActivity.papp);

                                        counter[0] = 6;
                                        faitButton.setVisibility(GONE);

                                        new CountDownTimer(5000, 1000){
                                            public void onTick(long millisUntilFinished){
                                                protocolTextView.setText("ETEINT TON APPAREIL ! \n"+String.valueOf(counter[0])+" secondes\npappOn = "+pappOn[0]);
                                                counter[0]--;
                                                protocolTextView.setText("ETEINT TON APPAREIL ! \n"+String.valueOf(counter[0])+" secondes\npappOn = "+pappOn[0]);
                                            }
                                            public  void onFinish(){
                                                protocolTextView.setText("ETEINT TON APPAREIL ! \n0 secondes\npappOn = "+pappOn[0]);
                                                faitButton.setVisibility(View.VISIBLE);
                                            }
                                        }.start();

                                        faitButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                pappOff[0] = Integer.parseInt(MainActivity.papp);
                                                faitButton.setText("AJOUTER APPAREIL");
                                                faitButton.setVisibility(GONE);
                                                diffPappOnOff[0] = pappOn[0]-pappOff[0];
                                                diffPappUnpluggedOff[0] = pappOff[0] - pappUnplugged[0];
                                                parent_activity.setPower(diffPappOnOff[0]);
                                                parent_activity.setstandbypower(diffPappUnpluggedOff[0]);
                                                protocolTextView.setText("OK on est bon \n pappOff = "+pappOff[0]+"\n pappOn = "+pappOn[0]+"\n pappUnplugged = "+pappUnplugged[0]);
                                                ajouterButton.setVisibility(View.VISIBLE);


                                                // Insertion d'un appareil
                                                Devices a = new Devices(parent_activity.getDb().getSize()+1,parent_activity.getSelected_device(), parent_activity.getPower(),parent_activity.getstandbypower(),0,0);
                                                parent_activity.getDb().insert(a);
                                                parent_activity.getDb().close();
                                                parent_activity.display_listview_of_Devices(false);
                                                makeText(parent_activity.getApplicationContext(),parent_activity.getSelected_device() + " ajouté. ", Toast.LENGTH_SHORT).show();

                                            }
                                        });
                                    }
                                });
                            }
                        });


                    }
                    else
                    {
                        protocolTextView.setText("ALLUME TON APPAREIL ! \n");

                        counter[0] = 6;
                        faitButton.setVisibility(GONE);

                        new CountDownTimer(5000, 1000){
                            public void onTick(long millisUntilFinished){
                                protocolTextView.setText("ALLUME TON APPAREIL ! \n"+String.valueOf(counter[0])+" secondes");
                                counter[0]--;
                                protocolTextView.setText("ALLUME TON APPAREIL ! \n"+String.valueOf(counter[0])+" secondes");
                            }
                            public  void onFinish(){
                                protocolTextView.setText("ALLUME TON APPAREIL ! \n0 secondes");
                                faitButton.setVisibility(View.VISIBLE);
                            }
                        }.start();


                        faitButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pappOn[0] = Integer.parseInt(MainActivity.papp);

                                counter[0] = 6;
                                faitButton.setVisibility(GONE);

                                new CountDownTimer(5000, 1000){
                                    public void onTick(long millisUntilFinished){
                                        protocolTextView.setText("ETEINT TON APPAREIL ! \n"+String.valueOf(counter[0])+" secondes\npappOn = "+pappOn[0]);
                                        counter[0]--;
                                        protocolTextView.setText("ETEINT TON APPAREIL ! \n"+String.valueOf(counter[0])+" secondes\npappOn = "+pappOn[0]);
                                    }
                                    public  void onFinish(){
                                        protocolTextView.setText("ETEINT TON APPAREIL ! \n0 secondes\npappOn = "+pappOn[0]);
                                        faitButton.setVisibility(View.VISIBLE);
                                    }
                                }.start();

                                faitButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        pappOff[0] = Integer.parseInt(MainActivity.papp);
                                        faitButton.setText("AJOUTER APPAREIL");
                                        faitButton.setVisibility(GONE);
                                        diffPappOnOff[0] = pappOn[0]-pappOff[0];
                                        parent_activity.setPower(diffPappOnOff[0]);;
                                        protocolTextView.setText("OK on est bon \n pappOff = "+pappOff[0]+"\n pappOn = "+pappOn[0]);
                                        ajouterButton.setVisibility(View.VISIBLE);


                                        // Insertion d'un appareil
                                        Devices a = new Devices(parent_activity.getDb().getSize()+1,parent_activity.getSelected_device(), parent_activity.getPower(),0,0,0);
                                        parent_activity.getDb().insert(a);
                                        parent_activity.getDb().close();
                                        parent_activity.display_listview_of_Devices(false);
                                        makeText(parent_activity.getApplicationContext(),parent_activity.getSelected_device() + " ajouté. ", Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }
                        });
                    }

                }
            });
            ajouterButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popUp.dismiss();
                }
            });
        }
        else
        {

            popUp.dismiss();
            Devices a = new Devices(parent_activity.getDb().getSize()+1,parent_activity.getSelected_device(), 0,0,0,0);
            parent_activity.getDb().insert(a);
            parent_activity.getDb().close();
            parent_activity.display_listview_of_Devices(false);
            makeText(parent_activity.getApplicationContext(),parent_activity.getSelected_device() + " ajouté. ", Toast.LENGTH_SHORT).show();
        }
    }

}
