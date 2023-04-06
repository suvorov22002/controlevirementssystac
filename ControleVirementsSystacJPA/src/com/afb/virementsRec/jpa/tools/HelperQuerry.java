package com.afb.virementsRec.jpa.tools;


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Helper
 * @author Francis KONCHOU
 * @version 1.0
 */
public class HelperQuerry {
    
    public static int LEFT = 1;
    public static int RIGHT = 2;
    public static int TAILLE_CODE_EVE = 6;
    public static int PHONES_LENGTH = 12;
    public static String PHONES_MASK = "2376";
    public static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH':'mm");
    public static SimpleDateFormat sdf_DATE = new SimpleDateFormat("dd/MM/yyyy");
    public static SimpleDateFormat sdf_HOUR = new SimpleDateFormat("HH':'mm");
    
    /**
     * Default Constructor
     */
    public HelperQuerry(){}
    
    
    /**
     * Retourne la liste des requetes necessaires pour la lecture des donnees dans Amplitude
     * @return
     */
    public static List<DataQuery> __getDefaultCBSQueries() {
    	
    	// Initialisation
    	List<DataQuery> dqs = new ArrayList<DataQuery>();
    	
    	// Ajout des requetes a la liste
    	dqs.add( new DataQuery("01", "Types de comptes", "select distinct cpro as code, lib as nom from bkprod ", null) ); // where cpro like '1%'
    	dqs.add( new DataQuery("02", "Recherche comptes client", "select bkcom.cpro, bkcom.age, bkcom.ncp, bkcom.clc, bkcli.nom, nvl(bkcli.pre, ' ') as pre, bkadcli.adr1, bkadcli.ville from bkcom, bkcli, bkadcli where bkcom.cli = bkcli.cli and bkadcli.cli = bkcli.cli and bkcom.cfe='N' and bkcom.ife='N' and bkcom.cli = ? ", null) );
    	
    	// Requete de Lecture du dernier numero d'evenement genere par type operation 
    	dqs.add( new DataQuery("03", "Dernier numero d'evenement genere", "select num from bkope where ope = ? and age='00099' ", null) );
    	
    	// Requete de MAJ du dernier numero d'evenement genere par type operation
    	dqs.add( new DataQuery("04", "MAJ du dernier numero d'evenement utilise", "update bkope set num = ? where ope = ? ", null) );
    	
    	// MAJ du solde indicatif du compte debiteur
    	dqs.add( new DataQuery("05", "MAJ du solde indicatif du compte", "update bkcom set sin = sin - ? where age = ? and ncp = ? and clc = ? ", null) );
    	
    	// MAJ du solde indicatif du compte crediteur
    	dqs.add( new DataQuery("06", "MAJ du solde indicatif du compte", "update bkcom set sin = sin + ? where age = ? and ncp = ? and clc = ? ", null) );

    	// Recherche d'un compte dans le CBS
    	dqs.add( new DataQuery("07", "Recherche Compte Client", "select bkcom.*, bkcli.nom, bkcli.ges from bkcom, bkcli where bkcli.cli = bkcom.cli and bkcom.age = ? and bkcom.ncp = ? and bkcom.clc = ?", null) );

    	// Recherche d'un compte dans le CBS
    	dqs.add( new DataQuery("08", "Recherche Compte Non Client", "select * from bkcom where dev='001' and  age = ? and ncp = ? and clc = ? and cfe='N' and ife='N'", null) );
    	
    	dqs.add( new DataQuery("09", "Recherche Compte", "select * from bkcom where bkcom.dev='001' and  bkcom.age = ? and bkcom.ncp = ? and bkcom.clc = ? and cfe='N' and ife='N'", null) );
    	
    	dqs.add( new DataQuery("10", "MAJ Solde Indicatif TFJ", "update bksin set mon = mon - ? where age = ? and ncp = ? ", null) );
    	dqs.add( new DataQuery("11", "MAJ Solde Indicatif TFJ2", "update bksin set mon = mon + ? where age = ? and ncp = ? ", null) );
    	    	   	
    	// MAJ du solde indicatif du compte debiteur
    	dqs.add( new DataQuery("12", "Controle du solde indicatif du compte", "select sin from bkcom where age = ? and ncp = ? and clc = ? ", null) );
    	    	
    	// Check des Transactions
    	dqs.add( new DataQuery("13", "Controle si un evenement existe", "select * from bkeve where age = ? and ncp = ? and clc = ? ", null) );
    	
    	dqs.add( new DataQuery("14", "Controle si un evenement existe", "select sin from bkcom where age = ? and ncp = ? and clc = ? ", null) );

    	dqs.add( new DataQuery("15", "Controle si un evenement existe", "select sin from bkcom where age = ? and ncp = ? and clc = ? ", null) );
    	
    	// Recherche d'un compte dans le CBS
    	dqs.add( new DataQuery("16", "Recherche Compte Non Client", "select * from bkcom where  dev='001' and age = ? and ncp = ? and cfe='N' and ife='N'", null) );
    	 
    	dqs.add( new DataQuery("17", "Controle si un evenement existe", "select count(*) as nb from BKEVE where ope = ? and eve = ? and ncp1 = ? and ncp2 = ? and mon1 = ? ", null) );
    	
    	dqs.add( new DataQuery("18", "Recherche Compte", "select * from bkcom where bkcom.dev='001' and  bkcom.age = ? and bkcom.ncp = ? and cfe='N' and ife='N'", null) );
    	
    	dqs.add( new DataQuery("19", "Controle si un evenement existe", "select * from BKEVE where ope = ? and eve = ? and ncp1 = ? and ncp2 = ? and mon1 = ? ", null) );
    	
    	// Retourne la liste
    	return dqs;
    	
    }
    
 
    /**
     * 
     * @param text
     * @param direction
     * @param lenght
     * @param addString
     * @return
     */
    public static String padText(String text, int direction, int lenght, String addString ){
        
        String s = text;
        
        if(s == null){
            
            for(int i=1; i<=lenght; i++) s += addString;
            
        }else{
            
            if(s.length() < lenght){
                
                while(s.length() < lenght){
                    s = direction == LEFT ? addString + s : s + addString;
                }
                
            }
            
        }
        
        s = s.substring(0, lenght);
        
        return s;
    }
    
    
        
    public static String getCode(){
        return new SimpleDateFormat("ddMMyyhhmmss").format(new java.util.Date());
    }
         
    public static String getCode2(){
        return new SimpleDateFormat("ddMMyy").format(new java.util.Date());
    }
       
    
    
    public static String espacement(double montant){
        try{
            return espacement(new DecimalFormat("#0.###").format(new BigDecimal(montant)).replaceAll(",", "."));
        }catch(Exception e){
            return "0";
        }
    }


    public static String espacement(String mnt){
        try{
        if(mnt.isEmpty()) return "0";
        String partieDeci = ((mnt.indexOf(".")>=0)?(mnt.substring(mnt.indexOf("."), mnt.length()) ):(""));
        if(mnt.indexOf(".")>=0) mnt = mnt.substring(0, mnt.indexOf("."));
        if(mnt.indexOf(" ")>=0) mnt = mnt.replaceAll(" ", "");
        
        if(mnt.length()<=3){
            return mnt + partieDeci;
        }else{
            
            int nbreParties = ((mnt.length()%3)==0)?( (int)Math.ceil( (mnt.length()/3)  ) ):(  (int)Math.ceil(Double.parseDouble( String.valueOf( (mnt.length()/3) ).split(",")[0] + ".8" ))    );
            //System.out.println("Montant : " + mnt + ", nbre de parties = " + nbreParties);
            int i=0; String s = "";
            while(i<nbreParties && mnt.length()>0){
                s = " " + mnt.substring( ((mnt.length()>=3)?(mnt.length()-3):(0)) , mnt.length()) + s;
                mnt = mnt.substring(0, ((mnt.length()>=3)?(mnt.length()-3):(mnt.length())) );
                i++;
            }
            if(s.startsWith(" ")) s = s.substring(1, s.length());
            return s + partieDeci;
        }
        }catch(Exception e){
            e.printStackTrace();
            return mnt;
        }
    }

    
    public static long getNbreJoursBetween(Date date1, Date date2){
        if(date1 == null || date2 == null) return 0;
        return (date2.getTime() - date1.getTime()) / (long)(1000 * 60 * 60 * 24);
    }
    
    
    
}