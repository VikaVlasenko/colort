package com.vvv.colort;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


import java.net.URL;
import java.util.ResourceBundle;
import java.lang.Math;

import static java.lang.Math.abs;

public class HelloController implements Initializable {


    public double R=0,G=0,B=0;
    public double iC=0,iM=0,iY=0,iK=0;   // CMYK
    public double iL=0,iA=0,iB=0;      // LAB
    public double iH=0,iS=0,iV=0;      // HSV


    public String prevModel="";

    public Label lblModel;
    public Pane panColor;
    public TextField textCMYK;
    public TextField textLAB;
    public TextField textHSV;
    @FXML    private ComboBox<String> cmbModel;

    @FXML  private Label lb1;
    @FXML  private Label lb2;
    @FXML  private Label lb3;
    @FXML  private Label lb4;

    @FXML private TextField text1;
    @FXML private TextField text2;
    @FXML private TextField text3;
    @FXML private TextField text4;

    @FXML private TextField textR;
    @FXML private TextField textG;
    @FXML private TextField textB;


    @FXML    private ColorPicker  colorPick;

    @FXML private Slider slider1;
    @FXML private Slider slider2;
    @FXML private Slider slider3;
    @FXML private Slider slider4;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<String> list= FXCollections.observableArrayList("CMYK","LAB","HSV/HSB");
        cmbModel.setItems(list);

        lb1.setVisible(false);lb2.setVisible(false);lb3.setVisible(false);lb4.setVisible(false);

        colorPick.setVisible(false);
        text1.setVisible(false);text2.setVisible(false);text3.setVisible(false);text4.setVisible(false);

        slider1.setVisible(false);        slider2.setVisible(false);        slider3.setVisible(false);
        slider4.setVisible(false);

        slider1.setValue(0);slider2.setValue(0);slider3.setValue(0);slider4.setValue(0);
        slider1.setMin(0);slider1.setMax(100);
        slider2.setMin(0);slider2.setMax(100);
        slider3.setMin(0);slider3.setMax(100);
        slider4.setMin(0);slider4.setMax(100);
        text1.setText("0");text2.setText("0");text3.setText("0");text4.setText("0");



//----------------------------------------------------------------------------------
//
//  Добавление обработчиков для скролингов
//
//----------------------------------------------------------------------------------
        slider1.valueProperty().addListener((observable, oldValue, newValue) -> {
            text1.setText(String.valueOf(newValue.intValue()));

            if(cmbModel.getSelectionModel().getSelectedItem().equals("CMYK"))
                iC = newValue.intValue();
            if(cmbModel.getSelectionModel().getSelectedItem().equals("LAB"))
                iL = newValue.intValue();
            if(cmbModel.getSelectionModel().getSelectedItem().equals("HSV/HSB"))
                iH = newValue.intValue();

            setColorPanel();
        });


        //----------------------------------------------------
        slider2.valueProperty().addListener((observable, oldValue, newValue) -> {

            text2.setText(String.valueOf(newValue.intValue()));

            if(cmbModel.getSelectionModel().getSelectedItem().equals("CMYK"))
                iM =  newValue.intValue();

            if(cmbModel.getSelectionModel().getSelectedItem().equals("LAB"))
                iA =  newValue.intValue();

            if(cmbModel.getSelectionModel().getSelectedItem().equals("HSV/HSB"))
                iS =  newValue.intValue();
            setColorPanel();

        });

        //--------------------------------
        slider3.valueProperty().addListener((observable, oldValue, newValue) -> {
            text3.setText(String.valueOf(newValue.intValue()));

            if(cmbModel.getSelectionModel().getSelectedItem().equals("CMYK"))
                iY = newValue.intValue();

            if(cmbModel.getSelectionModel().getSelectedItem().equals("LAB"))
                iB = newValue.intValue();

            if(cmbModel.getSelectionModel().getSelectedItem().equals("HSV/HSB"))
                iV = newValue.intValue();

            setColorPanel();

        });

        //----------------------------------------------------

        slider4.valueProperty().addListener((observable, oldValue, newValue) -> {
            text4.setText(String.valueOf(newValue.intValue()));
            if(cmbModel.getSelectionModel().getSelectedItem().equals("CMYK"))
                iK = newValue.intValue();
            setColorPanel();
        });



    }
//------------------------------------------------------------------------------------------
//
//  Отработка смены цвета в текстовом окне
//
//------------------------------------------------------------------------------------------

    public void text1Press()
    {
        int c= Integer.parseInt(text1.getText());
        slider1.setValue(c);
        setColorPanel();
    }
    public void text2Press()
    {
        int c= Integer.parseInt(text2.getText());
        slider2.setValue(c);
        setColorPanel();
    }

    public void text3Press()
    {
        int c= Integer.parseInt(text3.getText());
        slider3.setValue(c);
        setColorPanel();
    }

    public void text4Press()
    {
        int c= Integer.parseInt(text1.getText());
        slider4.setValue(c);
        setColorPanel();
    }


//------------------------------------------------------
//
//  Смена цвета
//
//------------------------------------------------------
    public void actionColorPick(javafx.event.ActionEvent actionEvent) {

        Color clr,t;
        clr=colorPick.getValue();

        //clr.getBlue()
        R=clr.getRed()*255;
        G=clr.getGreen()*255;
        B=clr.getBlue()*255;

        t=Color.rgb((int)R,(int)G,(int)B);
        //setColorPanel();

        rgbToCmyk();
        textCMYK.setText((int)iC+ "%   "+(int)iM+"%  "+(int)iY+"%  "+(int)iK+"%");
        if(cmbModel.getSelectionModel().getSelectedItem().equals("CMYK"))
        {
            text1.setText( String.valueOf((int)iC) );
            text2.setText( String.valueOf((int)iM) );
            text3.setText( String.valueOf((int)iY) );
            text4.setText( String.valueOf((int)iK) );
            slider1.setValue((int)iC);
            slider2.setValue((int)iM);
            slider3.setValue((int)iY);
            slider4.setValue((int)iK);
        }

        rgbToLab();
        textLAB.setText(iL+ "   "+iA+"  "+iB);

        if(cmbModel.getSelectionModel().getSelectedItem().equals("LAB"))
        {
            text1.setText( String.valueOf((int)iL) );
            text2.setText( String.valueOf((int)iA) );
            text3.setText( String.valueOf((int)iB) );
            slider1.setValue((int)iL);
            slider2.setValue((int)iA);
            slider3.setValue((int)iB);
        }


        rgbToHsv();
        textHSV.setText((int)iH+ "   "+(int)iS+"  "+(int)iV);
        if(cmbModel.getSelectionModel().getSelectedItem().equals("HSV/HSB"))
        {
            text1.setText( String.valueOf((int)iH) );
            text2.setText( String.valueOf((int)iS) );
            text3.setText( String.valueOf((int)iV) );
            slider1.setValue((int)iH);
            slider2.setValue((int)iS);
            slider3.setValue((int)iV);
        }


        //setColorPanel();

        Background confirmBackground = new Background(new BackgroundFill(t, CornerRadii.EMPTY, Insets.EMPTY));
        panColor.backgroundProperty().setValue(confirmBackground);

    }

    //-----------------------------------------------------------------
    //
    // Смена модели  цвета
    //
    //-----------------------------------------------------------------
    public void SelectCmbModel(javafx.event.ActionEvent actionEvent) {

        String s=cmbModel.getSelectionModel().getSelectedItem();

        lb1.setVisible(true);
        lb2.setVisible(true);
        lb3.setVisible(true);
        lb4.setVisible(false);

        text4.setVisible(false);
        slider4.setVisible(false);
        slider1.setVisible(true);slider2.setVisible(true);        slider3.setVisible(true);
        text1.setVisible(true);text2.setVisible(true);text3.setVisible(true);
        slider1.setMin(0);slider1.setMax(100);
        slider2.setMin(0);slider2.setMax(100);
        slider3.setMin(0);slider3.setMax(100);
        slider4.setMin(0);slider4.setMax(100);

        colorPick.setVisible(true);

        if(s.equals("CMYK"))
        {
            if(!prevModel.isEmpty())
               rgbToCmyk();

            lb4.setVisible(true);
            text4.setVisible(true);
            slider4.setVisible(true);
            lb1.setText("C");lb2.setText("M");lb3.setText("Y");lb4.setText("K");

            text1.setText(String.valueOf((int)iC));
            text2.setText(String.valueOf((int)iM));
            text3.setText(String.valueOf((int)iY));
            text4.setText(String.valueOf((int)iK));

            slider1.setValue((int)iC);
            slider2.setValue((int)iM);
            slider3.setValue((int)iY);
            slider4.setValue((int)iK);

        }

        if(s.equals("LAB")) {

            slider1.setMin(0);slider1.setMax(100);
            slider2.setMin(-128);slider2.setMax(128);
            slider3.setMin(-128);slider3.setMax(128);

            if(!prevModel.isEmpty())
                rgbToLab();

            lb1.setText("L");
            lb2.setText("A");
            lb3.setText("B");

            //  Установить параметры LAB в управление цветовой моделью
            text1.setText(String.valueOf((int)iL));
            text2.setText(String.valueOf((int)iA));
            text3.setText(String.valueOf((int)iB));

            slider1.setValue((int)iL);
            slider2.setValue((int)iA);
            slider3.setValue((int)iB);
        }


        if(s.equals("HSV/HSB"))
        {
            if(!prevModel.isEmpty())
                rgbToHsv();

            lb1.setText("H");
            lb2.setText("S");
            lb3.setText("V");
            slider1.setMin(0);slider1.setMax(360);

            text1.setText(String.valueOf((int)iH));
            text2.setText(String.valueOf((int)iS));
            text3.setText(String.valueOf((int)iV));

            slider1.setValue((int)iH);
            slider2.setValue((int)iS);
            slider3.setValue((int)iV);

        }

        setColorPanel();

//        Color t=Color.rgb((int)R,(int)G,(int)B);
//        Background confirmBackground = new Background(new BackgroundFill(t, CornerRadii.EMPTY, Insets.EMPTY));
//        panColor.backgroundProperty().setValue(confirmBackground);


        prevModel=s;
    }


    //-----------------------------------------------------------------
    //
    //  Установка цвета в большом окне и подписи цветов в текстовых окнах
    //
    //-----------------------------------------------------------------
    public  void setColorPanel()
    {
        Color t;

        if(cmbModel.getSelectionModel().getSelectedItem().equals("CMYK"))
        {
            textCMYK.setText((int)iC+ "%   "+(int)iM+"%  "+(int)iY+"%  "+(int)iK+"%");
            CMYKToRGB(iC,iM,iY,iK);
            rgbToLab();
            textLAB.setText(iL+ "   "+iA+"  "+iB);
            rgbToHsv();
            textHSV.setText((int)iH+ "   "+(int)iS+"  "+(int)iV);
        }

        if(cmbModel.getSelectionModel().getSelectedItem().equals("LAB"))
        {
            textLAB.setText(iL+ "   "+iA+"  "+iB);
            labToRgb();

            rgbToCmyk();
            textCMYK.setText((int)iC+ "%   "+(int)iM+"%  "+(int)iY+"%  "+(int)iK+"%");
            rgbToHsv();
            textHSV.setText((int)iH+ "   "+(int)iS+"  "+(int)iV);
        }

        if(cmbModel.getSelectionModel().getSelectedItem().equals("HSV/HSB"))
        {
            textHSV.setText((int)iH+ "   "+(int)iS+"  "+(int)iV);
            hsvToRgb();
            rgbToCmyk();
            textCMYK.setText((int)iC+ "%   "+(int)iM+"%  "+(int)iY+"%  "+(int)iK+"%");
            rgbToLab();
            textLAB.setText(iL+ "   "+iA+"  "+iB);

        }


        textR.setText(String.valueOf((int)R));
        textG.setText(String.valueOf((int)G));
        textB.setText(String.valueOf((int)B));

        //  Отображение цвета в панели справа
        t=Color.rgb((int)R,(int)G,(int)B);
        Background confirmBackground = new Background(new BackgroundFill(t, CornerRadii.EMPTY, Insets.EMPTY));
        panColor.backgroundProperty().setValue(confirmBackground);

    }

//----------------------------------------------------------
    public void CMYKToRGB(double C,double M,double Y,double K) {
        double c,m,k,y;

        c=C;m=M;y=Y;k=K;
        R = (abs(255 * (1 - c/100) * (1 - k/100)));
        if(R>255) R=255;
        G = (abs(255 * (1 - m/100) * (1 - k/100)));
        if(G>255) G=255;
        B = (abs(255 * (1 - y/100) * (1 - k/100)));
        if(B>255) B=255;
    }

//------------------------------------------

       // Convert RGB to CMYK
     public void rgbToCmyk() {
         // Normalize RGB values to the range [0, 1]
         double r = R / 255.0f;
         double g = G / 255.0f;
         double b = B / 255.0f;

         iK = 1 - Math.max(r, Math.max(g, b));
         iC = (1 - r - iK) / (1 -iK);
         iM = (1 - g - iK) / (1 - iK);
         iY = (1 - b - iK) / (1 - iK);

         // Handle the case where RGB is all 0
         if (iK == 1) {             iC = 0;             iM = 0;             iY = 0;         }

         iK*=100;
         iC*=100;
         iM*=100;
         iY*=100;
     }

//------------------------------------------
    public void  rgbToLab() {
        // Normalize the RGB values to the range [0, 1]
        double r,g,b;

         r = R / 255.0;
         g = G / 255.0;
         b = B / 255.0;

        r = (r > 0.04045) ? Math.pow((r + 0.055) / 1.055, 2.4) : r / 12.92;
        g = (g > 0.04045) ? Math.pow((g + 0.055) / 1.055, 2.4) : g / 12.92;
        b = (b > 0.04045) ? Math.pow((b + 0.055) / 1.055, 2.4) : b / 12.92;

        r *= 100;
        g *= 100;
        b *= 100;

        // Convert to XYZ
        double X = r * 0.4124564 + g * 0.3575761 + b * 0.1804375;
        double Y = r * 0.2126729 + g * 0.7151522 + b * 0.0721750;
        double Z = r * 0.0193339 + g * 0.1191920 + b * 0.9503041;

        // Normalize for D65 illuminant
        X /= 95.047;
        Y /= 100.000;
        Z /= 108.883;

        // Convert to LAB
        X = (X > 0.008856) ? Math.cbrt(X) : (X * 7.787) + (16.0 / 116);
        Y = (Y > 0.008856) ? Math.cbrt(Y) : (Y * 7.787) + (16.0 / 116);
        Z = (Z > 0.008856) ? Math.cbrt(Z) : (Z * 7.787) + (16.0 / 116);

        iL =(int)((116 * Y) - 16);
        iA =(int)( 500 * (X - Y));
        iB =(int)( 200 * (Y - Z));
    }

//--------------------------------------------------------------
public void labToRgb() {
    // Конвертация Lab to XYZ
    double y = (iL + 16) / 116;
    double x = iA / 500 + y;
    double z = y - iB / 200;

    double fy = Math.pow(y, 3);
    double fx = (x > 0.008856) ? Math.pow(x, 3) : (x - 16.0 / 116.0) / 7.787;
    double fz = (z > 0.008856) ? Math.pow(z, 3) : (z - 16.0 / 116.0) / 7.787;

    double X = 95.047 * fx;
    double Y = 100.0 * fy;
    double Z = 108.883 * fz;

    // Конвертация XYZ to RGB
    double r = (X * 3.2406 + Y * -1.5372 + Z * -0.4986)/100;
    double g = (X * -0.9689 + Y * 1.8758 + Z * 0.0415)/100;
    double b_val = (X * 0.0557 + Y * -0.2040 + Z * 1.0570)/100;

    // Преобразование RGB values
    double r_rescaled = (r > 0.0031308) ? (1.055 * Math.pow(r, 1/2.4) - 0.055) : 12.92 * r;
    double g_rescaled = (g > 0.0031308) ? (1.055 * Math.pow(g, 1/2.4) - 0.055) : 12.92 * g;
    double b_rescaled = (b_val > 0.0031308) ? (1.055 * Math.pow(b_val, 1/2.4) - 0.055) : 12.92 * b_val;
    // ----------------------------
    R = (int) Math.round(r_rescaled * 255);
    G = (int) Math.round(g_rescaled * 255);
    B = (int) Math.round(b_rescaled * 255);
   }

    //------------------------------------------------------
    public void rgbToHsv() {
        // Normalize RGB values to the range [0, 1]
        double r = R / 255.0f;
        double g = G / 255.0f;
        double b = B / 255.0f;

        double max = Math.max(r, Math.max(g, b));
        double min = Math.min(r, Math.min(g, b));

        double delta = max - min;

        double v, s = 0;
        double h;
        h = 0;
        v=max;

        if (delta != 0) {
            if (max == r) {
                h = ((g - b) / delta) % 6;
            } else if (max == g) {
                h = (b - r) / delta + 2;
            } else {
                h = (r - g) / delta + 4;
            }
            h *= 60;
            if (h < 0) h += 360; // Ensure hue is positive
        }

        // Calculate Saturation
        if (max != 0) {
            s = delta / max;
        }

        iH=h;
        iS=s*100;
        iV=v*100;

    }
//-----------------------------------------------------------------

    public void hsvToRgb() {

        double h,s,v;
        h=iH;
        s=iS;
        v=iV;

        // Ensure the input values are within the expected range
        h = h % 360; // Hue is in degrees
        s = Math.max(0, Math.min(100, s)); // Saturation is in percentage
        v = Math.max(0, Math.min(100, v)); // Value is in percentage

        double C = (s / 100) * (v / 100); // Chroma
        double X = C * (1 - Math.abs((h / 60) % 2 - 1));
        double m = (v / 100) - C;

        double rPrime, gPrime, bPrime;

        if (h < 60) {
            rPrime = C;
            gPrime = X;
            bPrime = 0;
        } else if (h < 120) {
            rPrime = X;
            gPrime = C;
            bPrime = 0;
        } else if (h < 180) {
            rPrime = 0;
            gPrime = C;
            bPrime = X;
        } else if (h < 240) {
            rPrime = 0;
            gPrime = X;
            bPrime = C;
        } else if (h < 300) {
            rPrime = X;
            gPrime = 0;
            bPrime = C;
        } else {
            rPrime = C;
            gPrime = 0;
            bPrime = X;
        }

        // Convert to RGB and apply the m offset
        R = Math.round((rPrime + m) * 255);
        G = Math.round((gPrime + m) * 255);
        B = Math.round((bPrime + m) * 255);
    }

}
