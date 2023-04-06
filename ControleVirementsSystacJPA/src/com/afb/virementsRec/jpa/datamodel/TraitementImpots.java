package com.afb.virementsRec.jpa.datamodel;

import java.io.Serializable;

import javax.persistence.*;

import com.afb.virementsRec.jpa.tools.HelperQuerry;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

/**
 * Entity implementation class for Entity: Traitement
 * Classe pour l'entit� Traitement
 * @author St�phane Mouafo
 *
 */
@Entity
@Table(name = "VIR_SYSTAC_TRAITEMENT_IMPOTS")
public class TraitementImpots implements Serializable{


	private static final long serialVersionUID = 1L;


	/**
	 * Identifiant
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "TRAITEMENT_ID")
	private Long id;
	
	@Column(name = "DATE_TRAITEMENT")
	private Date dateTraitement = new Date();
	
	@Column(name = "UTI_PORTAL")
	private String utiPortal;

	/**@Column(name = "AGSA")
	private String agsa;
	
	@Column(name = "AGE")
	private String age;
	
	@Column(name = "OPE")
	private String ope;

	@Column(name = "EVE")
	private String eve;

	@Column(name = "TYP")
	private String typ;    

	@Column(name = "NDOS")
	private String ndos = "               ";

	@Column(name = "AGE1")
	private String age1;

	@Column(name = "DEV1")
	private String dev1;
	
	@Column(name = "NCP1")
	private String ncp1;
	
	@Column(name = "SUF1")
	private String suf1 = "  ";
	
	@Column(name = "CLC1")
	private String clc1;
	
	@Column(name = "CLI1")
	private String cli1;
	
	@Column(name = "NOM1")
	private String nom1;

	@Column(name = "GES1")
	private String ges1;
	
	@Column(name = "SEN1")
	private String sen1 = "D";
	
	@Column(name = "MHT1")
	private Double mht1;
	
	@Column(name = "MON1")
	private Double mon1;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DVA1")
	private Date dva1;
	
	@Column(name = "EXO1")
	private String exo1 = "N";
	
	@Column(name = "SOL1")
	private Double sol1;
	
	@Column(name = "INDH1")
	private Double indh1 = 0.0000;
	
	@Column(name = "INDS1")
	private Double inds1 = 0.0000;
	
	@Column(name = "DESA1")
	private String desa1 = "     ";

	@Column(name = "DESA2")
	private String desa2 = "     ";
	
	@Column(name = "DESA3")
	private String desa3 = "     ";
	
	@Column(name = "DESA4")
	private String desa4 = "     ";
	
	@Column(name = "DESA5")
	private String desa5 = "     ";
	
	@Column(name = "AGE2")
	private String age2 = "     ";
	
	@Column(name = "DEV2")
	private String dev2 = "   ";
	
	@Column(name = "NCP2")
	private String ncp2 = "           ";
	
	@Column(name = "SUF2")
	private String suf2 = "  ";
	
	@Column(name = "CLC2")
	private String clc2 = "  ";
	
	@Column(name = "CLI2")
	private String cli2 = "               ";
	
	@Column(name = "NOM2")
	private String nom2 = "                                    ";

	@Column(name = "GES2")
	private String ges2 = "   ";
	
	@Column(name = "SEN2")
	private String sen2 = " ";
	
	@Column(name = "MHT2")
	private Double mht2;
	
	@Column(name = "MON2")
	private Double mon2;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DVA1")
	private Date dva2 = null;
	
	@Column(name = "DIN")
	private Date din = null;

	@Column(name = "EXO2")
	private String exo2 = " ";
	
	@Column(name = "SOL2")
	private Double sol2 = 0.0000;
	
	@Column(name = "INDH2")
	private Double indh2 = 0.0000;
	
	@Column(name = "INDS2")
	private Double inds2 = 0.0000;
	
	@Column(name = "DESC1")
	private String desc1 = "    ";

	@Column(name = "DESC2")
	private String desc2 = "    ";
	
	@Column(name = "DESC3")
	private String desc3 = "    ";
	
	@Column(name = "DESC4")
	private String desc4 = "    ";
	
	@Column(name = "DESA5")
	private String desc5 = "    ";
	
	@Column(name = "ETAB")
	private String etab;
	
	@Column(name = "GUIB")
	private String guib;
	
	@Column(name = "NOME")
	private String nome;
	
	@Column(name = "DOMI")
	private String domi;
	
	@Column(name = "ADB1")
	private String adb1 = "                              ";
	
	@Column(name = "ADB2")
	private String adb2 = "                              ";
	
	@Column(name = "ADB3")
	private String adb3 = "                              ";
	
	@Column(name = "VILB")
	private String vilb = "                              ";
	
	@Column(name = "CPOB")
	private String cpob = "      ";
	
	@Column(name = "CPAY")
	private String cpay = "   ";
	
	@Column(name = "ETABR")
	private String etabr;
	
	@Column(name = "GUIBR")
	private String guibr;
	
	@Column(name = "COMB")
	private String comb;
	
	@Column(name = "CLEB")
	private String cleb;
	
	@Column(name = "NOMB")
	private String nomb;
	
	@Column(name = "MBAN")
	private Double mban;
	
	@Column(name = "DVAB")
	private Date dvab;
	
	@Column(name = "CAI1")
	private String cai1 = "   ";
	
	@Column(name = "TYC1")
	private String tyc1 = " ";
	
	@Column(name = "DCAI1")
	private String dcai1 = "   ";
	
	@Column(name = "SCAI1")
	private String scai1 = " ";
	
	@Column(name = "MCAI1")
	private Double mcai1 = 0.0000;
	
	@Column(name = "ARRC1")
	private Double arrc1 = 0.0000;
	
	@Column(name = "CAI2")
	private String cai2 = "   ";
	
	@Column(name = "TYC2")
	private String tyc2 = " ";
	
	@Column(name = "DCAI2")
	private String dcai2 = "   ";
	
	@Column(name = "SCAI2")
	private String scai2 = " ";
	
	@Column(name = "MCAI2")
	private Double mcai2 = 0.0000;
	
	@Column(name = "ARRC2")
	private Double arrc2 = 0.0000;
	
	@Column(name = "DEV")
	private String dev;
	
	@Column(name = "MHT2")
	private Double mht;
	
	@Column(name = "MNAT")
	private Double mnat;
	
	@Column(name = "MBOR")
	private Double mbor = 0.0000;
	
	@Column(name = "NBOR")
	private String nbor = "      ";
	
	@Column(name = "NBLIG")
	private Integer nblig = 0;
	
	@Column(name = "TCAI2")
	private Double tcai2 = 1.0000000;
	
	@Column(name = "TCAI3")
	private Double tcai3 = 1.0000000;
	
	@Column(name = "NAT")
	private String nat;
	
	@Column(name = "NATO")
	private String nato = "      ";
	
	@Column(name = "OPEO")
	private String opeo = "   ";
	
	@Column(name = "EVEO")
	private String eveo = "      ";
	
	@Column(name = "PIEO")
	private String pieo= "           ";
	
	@Column(name = "DOU")
	private Date dou;
	
	@Column(name = "DCO")
	private Date dco;
	
	@Column(name = "ETA")
	private String eta;
	
	@Column(name = "ETAP")
	private String etap = "  ";
	
	@Column(name = "NBV")
	private Integer nbv = 0;
	
	@Column(name = "NVAL")
	private Integer nval = 0;
	
	@Column(name = "UTI")
	private String uti;
	
	@Column(name = "UTF")
	private String utf = "          ";

	@Column(name = "UTA")
	private String uta = "          ";
	
	@Column(name = "MOA")
	private Integer moa = 0;
	
	@Column(name = "MOF")
	private Integer mof = 0;
	
	@Column(name = "LIB1")
	private String lib1;
	
	/**
	 * Num�ro du contribuable
	 *
	@Column(name = "LIB2")
	private String lib2 = " ";

	/**
	 * Avis d'imposition
	 *
	@Column(name = "LIB3")
	private String lib3 = " ";
	
	@Column(name = "LIB4")
	private String lib4 = "                                        ";
	
	@Column(name = "LIB5")
	private String lib5 =  "                                        ";
	
	@Column(name = "LIB6")
	private String lib6 = "                                                  ";
	
	@Column(name = "LIB7")
	private String lib7 = "                                                  ";
	
	@Column(name = "LIB8")
	private String lib8 = "                                                  ";
	
	@Column(name = "LIB9")
	private String lib9 = "                                                  ";
	
	@Column(name = "LIB10")
	private String lib10 = "                                                  ";
	
	@Column(name = "AGEC")
	private String agec = "     ";
	
	@Column(name = "AGEP")
	private String agep = "     ";
	
	@Column(name = "INTR")
	private String intr = "C";
	
	@Column(name = "ORIG")
	private String orig = "T";
	
	@Column(name = "RLET")
	private String rlet;
	
	@Column(name = "CATR")
	private String catr = " ";
	
	@Column(name = "CEB")
	private String ceb = "O";
	
	@Column(name = "PLB")
	private String plb = "O";
	
	@Column(name = "CCO")
	private Integer cco;

	@Column(name = "DRET")
	private Date dret = null;
	
	@Column(name = "NATP")
	private String natp = "          ";
	
	@Column(name = "NUMP")
	private String nump = "                    ";
	
	@Column(name = "DATP")
	private Date datp = null;
	
	@Column(name = "NOMP")
	private String nomp = "                                    ";
	
	@Column(name = "AD1P")
	private String ad1p = "                              ";
	
	@Column(name = "AD2P")
	private String ad2p =  "                              ";
	
	@Column(name = "DELP")
	private String delp =  "                              ";
	
	@Column(name = "SERIE")
	private String serie = "  ";
	
	@Column(name = "NCHE")
	private String nche = "              ";
	
	@Column(name = "CHQL")
	private String chql = " ";
	
	@Column(name = "CHQC")
	private String chqc = " ";
	
	@Column(name = "CAB")
	private String cab = " ";
	
	@Column(name = "NCFF")
	private String ncff = "        ";
	
	@Column(name = "CSA")
	private String csa = " ";
	
	@Column(name = "CFRA")
	private String cfra = "O";
	
	@Column(name = "NEFF")
	private String neff = "        ";
	
	@Column(name = "TEFF")
	private String teff = " ";
	
	@Column(name = "DECH")
	private Date dech = null;
	
	@Column(name = "TIRE")
	private String tire = "      ";
	
	@Column(name = "AGTI")
	private String agti = " ";
	
	@Column(name = "AGRE")
	private String agre = " ";
	
	@Column(name = "NBJI")
	private Integer nbji = 0;
	
	@Column(name = "PTFC")
	private String ptfc = " ";
	
	@Column(name = "EFAV")
	private String efav = " ";
	
	@Column(name = "NAVL")
	private String navl = "      ";
	
	@Column(name = "EDOM")
	private String edom = " ";
	
	@Column(name = "EOPP")
	private String eopp = " ";
	
	@Column(name = "EFAC")
	private String efac = " ";
	
	@Column(name = "MOTI")
	private String moti = "    ";
	
	@Column(name = "ENVACC")
	private String envacc = " ";
	
	@Column(name = "ENOM")
	private String enom = "O";
	
	@Column(name = "VICL")
	private String vicl = "N";
	
	@Column(name = "TECO")
	private String teco = "N";
	
	@Column(name = "TENV")
	private String tenv = " ";
	
	@Column(name = "EXJO")
	private String exjo = "N";
	
	@Column(name = "ORG")
	private String org = "     ";
	
	@Column(name = "CAI3")
	private String cai3 = "   ";
	
	@Column(name = "MCAI3")
	private Double mcai3 = 0.0000;
	
	@Column(name = "FORC")
	private String forc = " ";
	
	@Column(name = "OCAI3")
	private String ocai3 = "  ";
	
	@Column(name = "NCAI3")
	private Integer ncai3 = 0;
	
	@Column(name = "CSP1")
	private String csp1 = "VPI";
	
	@Column(name = "CSP2")
	private String csp2 = "          ";
	
	@Column(name = "CSP3")
	private String csp3 = "R";
	
	@Column(name = "CSP4")
	private String csp4 = "          ";
	
	@Column(name = "CSP5")
	private String csp5 = "          ";
	
	@Column(name = "NDOM")
	private String ndom = "                         ";
	
	@Column(name = "CMOD")
	private String cmod = "          ";
	
	@Column(name = "DEVF")
	private String devf;
	
	@Column(name = "NCPF")
	private String ncpf = "           ";
	
	@Column(name = "SUFF")
	private String suff = "  ";
	
	@Column(name = "MONF")
	private Double monf = 0.0;
	
	@Column(name = "DVAF")
	private Date dvaf = null;
	
	@Column(name = "EXOF")
	private String exof = " ";
	
	@Column(name = "AGEE")
	private String agee = "     ";
	
	@Column(name = "DEVE")
	private String deve = "   ";
	
	@Column(name = "NCPE")
	private String ncpe = "           ";
	
	@Column(name = "SUFE")
	private String sufe = "  ";
	
	@Column(name = "CLCE")
	private String clce = "  ";
	
	@Column(name = "NCPI")
	private String ncpi = "           ";
	
	@Column(name = "SUFI")
	private String sufi = "  ";
	
	@Column(name = "MIMP")
	private Double mimp = 0.0000;
	
	@Column(name = "DVAI")
	private Date dvai = null;
	
	@Column(name = "NCPP")
	private String ncpp = "           ";
	
	@Column(name = "SUFP")
	private String sufp = "   ";
	
	@Column(name = "PRGA")
	private Double prga = 0.0000000;
	
	@Column(name = "MRGA")
	private Double mrga = 0.0000;
	
	@Column(name = "TERM")
	private String term = " ";
	
	@Column(name = "TVAR")
	private String tvar = "t";

	@Column(name = "INTP")
	private String intp = " ";

	@Column(name = "CAP")
	private String cap = " ";

	@Column(name = "PRLL")
	private String prll = " ";

	@Column(name = "ANO")
	private String ano = " ";

	@Column(name = "ETAB1")
	private String etab1 = "          ";

	@Column(name = "GUIB1")
	private String guib1 = "     ";

	@Column(name = "COM1B")
	private String com1b = "               ";
	
	@Column(name = "ETAB2")
	private String etab2 = "          ";
	
	@Column(name = "GUIB2")
	private String guib2 = "     ";
	
	@Column(name = "COM2B")
	private String com2b = "               ";
	
	@Column(name = "TCOM1")
	private Double tcom1 = 0.0000000;
	
	@Column(name = "MCOM1")
	private Double mcom1 = 0.0000;
	
	@Column(name = "TCOM2")
	private Double tcom2 = 0.0000000;
	
	@Column(name = "MCOM2")
	private Double mcom2 = 0.0000;
	
	@Column(name = "TCOM3")
	private Double tcom3 = 0.0000000;
	
	@Column(name = "MCOM3")
	private Double mcom3 = 0.0000;
	
	@Column(name = "FRAI1")
	private Double frai1 = 0.0000;
	
	@Column(name = "FRAI2")
	private Double frai2 = 0.0000;
	
	@Column(name = "FRAI3")
	private Double frai3 = 0.0000;
	
	@Column(name = "TTAX1")
	private Double ttax1 = 0.0000000;
	
	@Column(name = "MTAX1")
	private Double mtax1 = 0.0000;
	
	@Column(name = "TTAX2")
	private Double ttax2 = 0.0000000;
	
	@Column(name = "MTAX2")
	private Double mtax2 = 0.0000;
	
	@Column(name = "TTAX3")
	private Double ttax3 = 0.0000000;
	
	@Column(name = "MTAX3")
	private Double mtax3 = 0.0000;
	
	@Column(name = "MNT1")
	private Double mnt1;
	
	@Column(name = "MNT2")
	private Double mnt2 = 0.0000;
	
	@Column(name = "MNT3")
	private Double mnt3 = 0.0000;
	
	@Column(name = "MNT4")
	private Double mnt4 = 0.0000;
	
	@Column(name = "MNT5")
	private Double mnt5 = 0.0000;
	
	@Column(name = "TYC3")
	private String tyc3 = " ";
	
	@Column(name = "DCAI3")
	private String dcai3 = "   ";
	
	@Column(name = "SCAI3")
	private String scai3 = " ";
	
	@Column(name = "ARRC3")
	private Double arrc3 = 0.0000;
	
	@Column(name = "MHTD")
	private Double mhtd = 0.0000;
	
	@Column(name = "TCAI4")
	private Double tcai4 = 1.0000000;
	
	@Column(name = "TOPE")
	private String tope = "   ";
	
	@Column(name = "IMG")
	private String img = "N";
	
	@Column(name = "DSAI")
	private Date dsai;
	
	@Column(name = "HSAI")
	private String hsai;
	
	@Column(name = "PAYSP")
	private String paysp = "   ";
	
	@Column(name = "PDELP")
	private String pdelp = "                                             ";
	
	@Column(name = "MANDA")
	private String manda = "N";
	
	@Column(name = "REFDOS")
	private String refdos = "            ";
	
	@Column(name = "TCHFR")
	private Double tchfr = 0.0000000;
	
	*/
	
	
	private String agsa = "00001"; 			// agence de saisie CHAR 5
	private String age = "00001"; 			// Agence CHAR 5 1 t001Agence cacc
	private String ope; 			// Code opération CHAR 3 2 bkope ope
	private String eve; 			// Numéro d'évènement CHAR 6 3
	private String typ = "100"; 			// Type rattaché CHAR 3
	private String ndos = HelperQuerry.padText("", HelperQuerry.RIGHT, 15, " ");;			// Numéro de dossier rattaché CHAR 15
	private String age1; 			// Agence compte 1 CHAR 5 t001Agence cacc
	private String dev1; 			// Code devise compte 1 CHAR 3 bkcom dev
	private String ncp1; 			// Numéro de compte 1 CHAR 11 bkcom ncp
	private String suf1=HelperQuerry.padText("", HelperQuerry.RIGHT, 2, " ");; 			// Suffixe compte 1 CHAR 2 bkcom suf
	private String clc1; 			// Clé de contrôle compte 1 CHAR 2
	private String cli1; 			// Code client compte 1 CHAR 15 bkcli cli
	private String nom1; 			// Nom client compte 1 CHAR 36
	private String ges1; 			// Code gestionnaire compte 1 CHAR 3 t035Gestionnaires cacc
	private String sen1 = "D"; 			// Sens opération compte 1 (D/C) CHAR 1
	private Double mht1; 			// Montant nominal en devise du compte à débiter DECIMAL 19 - 4
	private Double mon1 = 0d; 		// Montant net compte 1 DECIMAL 19 - 4
	private Date dva1  = new Date(); 				// Date de valeur compte 1 DATE
	private String exo1 = "N"; 			// Exonération de commission de mouvement compte 1 CHAR 1
	private Double sol1 = 0d; 		// Solde compte 1 avant opération DECIMAL 19 - 4
	private Double indh1 = 0d; 		// Indisponible hors SBF compte 1 avant opération DECIMAL 19 - 4
	private Double inds1 = 0d; 		// Indisponible SBF compte 1 avant opération DECIMAL 19 - 4
	private String desa1 = HelperQuerry.padText("", HelperQuerry.RIGHT, 4, " "); 			// Code désaccord 1 à la saisie CHAR 4 t058Desaccords cacc
	private String desa2 = HelperQuerry.padText("", HelperQuerry.RIGHT, 4, " "); 			// Code désaccord 2 à la saisie CHAR 4 t058Desaccords cacc
	private String desa3 = HelperQuerry.padText("", HelperQuerry.RIGHT, 4, " ");			// Code désaccord 3 à la saisie CHAR 4 t058Desaccords cacc
	private String desa4 = HelperQuerry.padText("", HelperQuerry.RIGHT, 4, " "); 			// Code désaccord 4 à la saisie CHAR 4 t058Desaccords cacc
	private String desa5 = HelperQuerry.padText("", HelperQuerry.RIGHT, 4, " "); // Code désaccord 5 à la saisie CHAR 4 t058Desaccords cacc
	private String age2; // Agence compte 2 CHAR 5 bkcom age
	private String dev2; // Code devise compte 2 CHAR 3 bkcom dev
	private String ncp2; // Numéro de compte 2 CHAR 11 bkcom ncp
	private String suf2; // Suffixe compte 2 CHAR 2 bkcom suf
	private String clc2; // Clé de contrôle compte 2 CHAR 2
	private String cli2; // Code client compte 2 CHAR 15 bkcli cli
	private String nom2; // Nom client compte 2 CHAR 36
	private String ges2; // Code gestionnaire compte 2 CHAR 3 t035Gestionnaires cacc
	private String sen2 = " "; // C Sens opération compte 2 (D/C) CHAR 1
	private Double mht2 = 0d; // Montant nominal en devise du compte à créditer DECIMAL 19 - 4
	private Double mon2 = 0d; // Montant net compte 2 DECIMAL 19 - 4
	private Date dva2 = null;// new Date(); // Date de valeur compte 2 DATE
	private Date din = null; //new Date(); // Date d'indisponible DATE
	private String exo2 = " "; //"O"; // Exonération de commission de mouvement compte 2 CHAR 1
	private Double sol2 = 0d; // Solde compte 2 avant opération DECIMAL 19 - 4
	private Double indh2 = 0d; // Indisponible hors SBF compte 2 avant opération DECIMAL 19 - 4
	private Double inds2 = 0d; // Indisponible SBF compte 2 avant opération DECIMAL 19 - 4
	private String desc1 = HelperQuerry.padText("", HelperQuerry.RIGHT, 4, " "); // Code désaccord 1 compte à créditer CHAR 4 t058Desaccords cacc
	private String desc2 = HelperQuerry.padText("", HelperQuerry.RIGHT, 4, " "); // Code désaccord 2 compte à créditer CHAR 4 t058Desaccords cacc
	private String desc3 = HelperQuerry.padText("", HelperQuerry.RIGHT, 4, " "); // 3 compte à créditer CHAR 4 t058Desaccords cacc
	private String desc4 = HelperQuerry.padText("", HelperQuerry.RIGHT, 4, " "); // Code désaccord 4 compte à créditer CHAR 4 t058Desaccords cacc
	private String desc5 = HelperQuerry.padText("", HelperQuerry.RIGHT, 4, " "); // Code désaccord 5 compte à créditer CHAR 4 t058Desaccords cacc
	private String etab = HelperQuerry.padText("", HelperQuerry.RIGHT, 5, " "); // Code établissement bancaire de règlement CHAR 5 bkbqe etab
	private String guib = HelperQuerry.padText("", HelperQuerry.RIGHT, 5, " "); // Code guichet CHAR 5
	private String nome = HelperQuerry.padText("", HelperQuerry.RIGHT, 40, " "); // Nom de l'établissement bancaire CHAR 40
	private String domi = HelperQuerry.padText("", HelperQuerry.RIGHT, 30, " "); // Domiciliation CHAR 30
	private String adb1 = HelperQuerry.padText("", HelperQuerry.RIGHT, 30, " "); // Adresse banque 1 CHAR 30
	private String adb2 = HelperQuerry.padText("", HelperQuerry.RIGHT, 30, " "); // Adresse banque 2 CHAR 30
	private String adb3 = HelperQuerry.padText("", HelperQuerry.RIGHT, 30, " "); // Adresse banque 3 CHAR 30
	private String vilb = HelperQuerry.padText("", HelperQuerry.RIGHT, 30, " "); // Ville banque CHAR 30
	private String cpob = HelperQuerry.padText("", HelperQuerry.RIGHT, 6, " "); // Code postal CHAR 6
	private String cpay = HelperQuerry.padText("", HelperQuerry.RIGHT, 3, " "); // Code pays banque CHAR 3
	private String etabr = HelperQuerry.padText("", HelperQuerry.RIGHT, 5, " "); // Code établissement de règlement CHAR 5
	private String guibr = HelperQuerry.padText("", HelperQuerry.RIGHT, 5, " "); // Code guichet de règlement CHAR 5
	private String comb = HelperQuerry.padText("", HelperQuerry.RIGHT, 25, " "); // Numéro de compte CHAR 25
	private String cleb = HelperQuerry.padText("", HelperQuerry.RIGHT, 2, " "); // Clé RIB CHAR 2
	private String nomb = HelperQuerry.padText("", HelperQuerry.RIGHT, 30, " "); // Nom client autre banque CHAR 30
	private Double mban = 0d; // Montant net banque DECIMAL 19 - 4
	private Date dvab; // Date de valeur banque DATE
	private String cai1 = HelperQuerry.padText("", HelperQuerry.RIGHT, 3, " "); // Numéro de caisse devise nationale CHAR 3 t004Caisses cacc
	private String tyc1 = " "; // Type de caisse à débiter CHAR 1
	private String dcai1 = HelperQuerry.padText("", HelperQuerry.RIGHT, 3, " "); // Code devise caisse à débiter CHAR 3
	private String scai1 = " "; // Sens caisse devise nationale (D/C) CHAR 1
	private Double mcai1 = 0d; // Montant caisse devise nationale DECIMAL 19 - 4
	private Double arrc1 = 0d; // Montant arrondi caisse à débiter (retrait) DECIMAL 19 - 4
	private String cai2 = HelperQuerry.padText("", HelperQuerry.RIGHT, 3, " "); // ; //Numéro de caisse autres devises CHAR 3 t004Caisses cacc
	private String tyc2 = " "; // Type de caisse à créditer CHAR 1
	private String dcai2 = HelperQuerry.padText("", HelperQuerry.RIGHT, 3, " "); // Code devise caisse à créditer CHAR 3
	private String scai2 = " "; // Sens caisse autre devise (D/C) CHAR 1
	private Double mcai2 = 0d; // Montant caisse autre devise DECIMAL 19 - 4
	private Double arrc2 = 0d; // Montant arrondi caisse à créditer DECIMAL 19 - 4
	private String dev; // Code devise de la transaction CHAR 3 t005DevisesN cacc
	private Double mht = 0d; // Montant nominal opération (hors taxes) DECIMAL 19 - 4
	private Double mnat = 0d; // Contrevaleur en devise nationale DECIMAL 19 - 4
	private Double mbor = 0d; // Montant du bordereau DECIMAL 19 - 4
	private String nbor = HelperQuerry.padText("", HelperQuerry.RIGHT, 6, " "); // Numéro de bordereau CHAR 6
	private Integer nblig = 0; // Nombre de lignes constituant un lot saisi SMALLINT
	private Double tcai2 = 1d; // Taux de change devise transaction/devise compte DECIMAL 15 - 7
	private Double tcai3 = 1d; // Taux de change devise transaction/devise nationale DECIMAL 15 - 7
	private String nat = "VIRBAN"; // Nature de la transaction CHAR 6 t052NaturOper cacc
	private String nato = HelperQuerry.padText("", HelperQuerry.RIGHT, 6, " "); // Nature d'origine CHAR 6
	private String opeo = HelperQuerry.padText("", HelperQuerry.RIGHT, 3, " "); // Code opération d'origine CHAR 3
	private String eveo = HelperQuerry.padText("", HelperQuerry.RIGHT, 6, " "); // Numéro d'évènement d'origine CHAR 6
	private String pieo = HelperQuerry.padText("", HelperQuerry.RIGHT, 11, " "); // Numéro de pièce comptable d'origine CHAR 11
	private Date dou = new Date(); // Date de création DATE
	private Date dco; // Date comptable DATE
	private String eta = "VA"; // Etat de l'évènement (VA/AT/FO/VF/IG/IF/AB) CHAR 2
	private String etap = "  "; // Etat précédent de l'évènement CHAR 2
	private Integer nbv = 0; // Nombre de validations requises DECIMAL 1 - 0
	private Integer nval = 0; // validation effectué DECIMAL 1 - 0
	private String uti; // Code utilisateur ayant initié l'évènement CHAR 10 evuti cuti
	private String utf = HelperQuerry.padText("", HelperQuerry.RIGHT, 10, " "); // Code utilisateur ayant rappelé pour forcer CHAR 10 evuti cuti
	private String uta = HelperQuerry.padText("", HelperQuerry.RIGHT, 10, " "); // Code utilisateur ayant autorisé CHAR 10 evuti cuti
	private Double moa = 0d; // Clé de forçage DECIMAL 6 - 0
	private Double mof = 0d; // Clé de forçage retrait déplacé DECIMAL 6 - 0
	private String lib1 = " "; //"OPERATION BUS "; // Libellé libre CHAR 40
	private String lib2 = HelperQuerry.padText("", HelperQuerry.RIGHT, 40, " "); // Idem CHAR 40
	private String lib3 = HelperQuerry.padText("", HelperQuerry.RIGHT, 40, " "); // Idem CHAR 40
	private String lib4 = HelperQuerry.padText("", HelperQuerry.RIGHT, 40, " "); // Idem CHAR 40
	private String lib5 = HelperQuerry.padText("", HelperQuerry.RIGHT, 40, " "); // Idem CHAR 40
	private String lib6 = HelperQuerry.padText("", HelperQuerry.RIGHT, 50, " "); // Libellé libre CHAR 50
	private String lib7 = HelperQuerry.padText("", HelperQuerry.RIGHT, 50, " "); // Idem CHAR 50
	private String lib8 = HelperQuerry.padText("", HelperQuerry.RIGHT, 50, " "); // Idem CHAR 50
	private String lib9 = HelperQuerry.padText("", HelperQuerry.RIGHT, 50, " "); // Libellé réservé pour mémorisation du code lettrage CHAR 50
	private String lib10 = HelperQuerry.padText("", HelperQuerry.RIGHT, 50, " "); // Libellé libre CHAR 50
	private String agec = HelperQuerry.padText("", HelperQuerry.RIGHT, 5, " "); // Agence de centralisation pour service étranger CHAR 5 t001Agence cacc
	private String agep = HelperQuerry.padText("", HelperQuerry.RIGHT, 5, " "); // Agence de provenance CHAR 5
	private String intr = "C"; // Code édition livret CHAR 1
	private String orig = "T"; //"S"; // = "I";
	private String rlet = HelperQuerry.padText("", HelperQuerry.RIGHT, 8, " "); // Référence de lettrage CHAR 8
	private String catr = " "; // Code évènement à transférer CHAR 1
	private String ceb = "O"; //N Opération compensable (O/N) CHAR 1
	private String plb = "O"; // Place bancable (O/N) CHAR 1
	private String cco = "0"; // Code compensation (0/1/2/3/4/5) CHAR 1
	private Date dret; // Date de retour compensation DATE
	private String natp = HelperQuerry.padText("", HelperQuerry.RIGHT, 10, " "); // Nature pièce d'identité CHAR 10
	private String nump = HelperQuerry.padText("", HelperQuerry.RIGHT, 20, " "); // Numéro de pièce d'identité CHAR 20
	private String datp; // Date de délivrance de la pièce d'identité DATE
	private String nomp = HelperQuerry.padText("", HelperQuerry.RIGHT, 36, " "); // Nom du porteur CHAR 36
	private String ad1p = HelperQuerry.padText("", HelperQuerry.RIGHT, 30, " "); // Adresse 1 du porteur CHAR 30
	private String ad2p = HelperQuerry.padText("", HelperQuerry.RIGHT, 30, " "); // Adresse 2 du porteur CHAR 30
	private String delp = HelperQuerry.padText("", HelperQuerry.RIGHT, 30, " "); // Lieu de délivrance de la pièce d'identité porteur CHAR 30
	private String serie = HelperQuerry.padText("", HelperQuerry.RIGHT, 2, " "); // Série de numéro de chèque CHAR 2
	private String nche = HelperQuerry.padText("", HelperQuerry.RIGHT, 14, " "); // Numéro de chèque CHAR 14 bkchq nche
	private String chql = " "; //"N"; // Chèque client (O/N) CHAR 1
	private String chqc = " "; // "N"; // Code chèque certifié (O/N) CHAR 1
	private String cab =  " "; //"N"; // Chèque autre banque (O/N) CHAR 1
	private String ncff = HelperQuerry.padText("", HelperQuerry.RIGHT, 8, " "); // émis en devise CHAR 8
	private String csa = " "; // Type de chèque (S/A) CHAR 1
	private Date dech; // Date d'échéance DATE
	private String tire = HelperQuerry.padText("", HelperQuerry.RIGHT, 6, " "); // Code tiré autre banque CHAR 6 bktir cod
	private String agti = " "; // Agios à la charge du tiré (O/N) CHAR 1
	private String agre = " "; // Agios à la charge du tiré à rétrocéder (O/N) CHAR 1
	private Integer nbji = 0; // Nombre de jours d'intérêts DECIMAL 3 - 0
	private String ptfc = " "; // Effet généré en portefeuille centralisé (O/N) CHAR 1
	private String efav = " "; // Effet avalisé (O/N) CHAR 1
	private String navl = HelperQuerry.padText("", HelperQuerry.RIGHT, 6, " "); // Numéro d'aval (caution) CHAR 6
	private String edom = " "; // Effet domicilié (O/N) CHAR 1
	private String eopp = " "; // Effet en opposition (O/N) CHAR 1
	private String efac = " "; // Effet accepté (O/N) CHAR 1
	private String moti = HelperQuerry.padText("", HelperQuerry.RIGHT, 4, " "); // Motif de non acceptation CHAR 4
	private String envacc = " "; // Effet à envoyer à l'acceptation (O/n) CHAR 1
	private String enom = "O"; //"N"; // Edition du nom CHAR 1
	private String vicl = "N"; // Virement même client CHAR 1
	private String teco = "N"; //" "; // Télex ou courrier CHAR 1
	private String tenv = " "; // Télex envoyé (O/N) CHAR 1
	private String exjo = "N"; // Virement à exécution jour (O/N) CHAR 1
	private String org = HelperQuerry.padText("", HelperQuerry.RIGHT, 5, " "); // Code organisme domiciliateur de prélèvements CHAR 5
	private String cai3 = HelperQuerry.padText("", HelperQuerry.RIGHT, 3, " "); // Numéro de caisse T.C. CHAR 3 t004Caisses cacc
	private Double mcai3 = 0d; // Montant caisse T.C. DECIMAL 19 - 4
	private String forc = " "; // Opération pouvant être forcée CHAR 1
	private String ocai3 = "  "; // Organisme T.C. CHAR 2 t053OrganismeTC cacc
	private Integer ncai3 = 0; // Nombre de T.C. DECIMAL 3 - 0
	private String csp1 = "VPI"; //"VBUSS"; // Code spécifique CHAR 10
	private String csp2 = HelperQuerry.padText("", HelperQuerry.RIGHT, 10, " "); // Idem CHAR 10
	private String csp3 = "R";//HelperQuerry.padText("", HelperQuerry.RIGHT, 10, " "); // Idem CHAR 10
	private String csp4 = HelperQuerry.padText("", HelperQuerry.RIGHT, 10, " "); // Idem CHAR 10
	private String csp5 = HelperQuerry.padText("", HelperQuerry.RIGHT, 10, " "); // Idem CHAR 10
	private String ndom = HelperQuerry.padText("", HelperQuerry.RIGHT, 25, " "); // Numéro de domiciliation (étranger) CHAR 25
	private String cmod = HelperQuerry.padText("", HelperQuerry.RIGHT, 10, " "); // Code motif déclaré CHAR 10
	private String devf = "001"; // Devise compte de frais CHAR 3 bkcom dev
	private String ncpf; // Numéro de compte de frais CHAR 11 bkcom ncp
	private String suff = "  "; // Suffixe du compte de frais CHAR 2 bkcom suf
	private Double monf = 0d; // Montant du compte de frais DECIMAL 19 - 4
	private Date dvaf; // Date de valeur du compte de frais DATE
	private String exof = " "; // Exonération de commission compte de frais CHAR 1
	private String agee = HelperQuerry.padText("", HelperQuerry.RIGHT, 5, " "); // Compte d'encaissement/engagement : agence CHAR 5 bkcom ncp
	private String deve = HelperQuerry.padText("", HelperQuerry.RIGHT, 3, " "); // Compte d'encaissement/engagement : code devise CHAR 3 bkcom dev
	private String ncpe = HelperQuerry.padText("", HelperQuerry.RIGHT, 11, " "); // Compte d'encaissement/engagement : numéro CHAR 11 bkcom ncp
	private String sufe = HelperQuerry.padText("", HelperQuerry.RIGHT, 2, " "); // Compte d'encaissement/engagement : suffixe CHAR 2 bkcom suf
	private String clce = "  "; // : clé de contrôl CHAR 2
	private String ncpi = HelperQuerry.padText("", HelperQuerry.RIGHT, 11, " "); // Numéro de compte d'impayé CHAR 11
	private String sufi = "  "; // Suffixe compte d'impayé CHAR 2
	private Double mimp = 0d; // Montant frais d'impayés DECIMAL 19 - 4
	private Date dvai; // Date de valeur impayé DATE
	private String ncpp = HelperQuerry.padText("", HelperQuerry.RIGHT, 11, " "); // Numéro de compte de provision CHAR 11
	private String sufp = "  "; // Suffixe compte de provision CHAR 2
	private Double prga = 0d; // Pourcentage retenue de garantie DECIMAL 10 - 7
	private Double mrga = 0d; // Montant provision en devise du compte DECIMAL 19 - 4
	private String term = " "; // Terme du dossier CHAR 1
	private String tvar = "t"; // TVA récupérée (O/N) CHAR 1
	private String intp = " "; // Intérêts précomptés (O/N) CHAR 1
	private String cap = " "; // Capitalisation des intérêts (O/N) CHAR 1
	private String prll = " "; // Prélèvements libératoires CHAR 1
	private String ano = " "; // BDC anonyme (O/N) CHAR 1
	private String etab1 = HelperQuerry.padText("", HelperQuerry.RIGHT, 5, " "); // Code établissement bancaire 1 CHAR 5 bkbqe etab
	private String guib1 = HelperQuerry.padText("", HelperQuerry.RIGHT, 5, " "); // Code guichet bancaire 1 CHAR 5 bkbqe guib
	private String com1b = HelperQuerry.padText("", HelperQuerry.RIGHT, 15, " "); // Numéro de compte 1 client autre banque CHAR 15
	private String etab2 = HelperQuerry.padText("", HelperQuerry.RIGHT, 5, " "); // Code établissement bancaire 2 CHAR 5 bkbqe etab
	private String guib2 = HelperQuerry.padText("", HelperQuerry.RIGHT, 5, " "); // Code guichet bancaire 2 CHAR 5 bkbqe guib
	private String com2b = HelperQuerry.padText("", HelperQuerry.RIGHT, 15, " "); // Numéro de compte client 2 autre banque CHAR 15
	private Double tcom1 = 0d; // Taux de commission 1 DECIMAL 15 - 7
	private Double mcom1 = 0d; // Montant commission 1 en devise nationale DECIMAL 19 - 4
	private Double tcom2 = 0d; // Taux de commission 2 DECIMAL 15 - 7
	private Double mcom2 = 0d; // Montant commission 2 en devise nationale DECIMAL 19 - 4
	private Double tcom3 = 0d; // Taux de commission 3 DECIMAL 15 - 7
	private Double mcom3 = 0d; // Montant commission 3 en devise nationale DECIMAL 19 - 4
	private Double frai1 = 0d; // Montant frais 1 en devise nationale DECIMAL 19 - 4
	private Double frai2 = 0d; // Montant frais 2 en devise nationale DECIMAL 19 - 4
	private Double frai3 = 0d; // Montant frais 3 en devise nationale DECIMAL 19 - 4
	private Double ttax1 = 0d; // Taux de taxe 1 DECIMAL 15 - 7
	private Double mtax1 = 0d; // Montant taxe 1 en devise nationale DECIMAL 19 - 4
	private Double ttax2 = 0d; // Taux de taxe 2 DECIMAL 15 - 7
	private Double mtax2 = 0d; // Montant taxe 2 en devise nationale DECIMAL 19 - 4
	private Double ttax3 = 0d; // Taux de taxe 3 DECIMAL 15 - 7
	private Double mtax3 = 0d; // Montant taxe 3 en devise nationale DECIMAL 19 - 4
	private Double mnt1 = 0d; // Montant libre DECIMAL 19 - 4
	private Double mnt2 = 0d; // Idem DECIMAL 19 - 4
	private Double mnt3 = 0d; // Idem DECIMAL 19 - 4
	private Double mnt4 = 0d; // Idem DECIMAL 19 - 4
	private Double mnt5 = 0d; // Idem DECIMAL 19 - 4
	private String tyc3 = " "; //"N";
	private String dcai3 = HelperQuerry.padText("", HelperQuerry.RIGHT, 3, " "); // Devise numérique de la caisse CHAR 3 t002DevisesA dev
	private String scai3 = " "; // Sens de la caisse CHAR 1
	private Double arrc3 = 0d; // Arrondi caisse DECIMAL 19 - 4
	private Double mhtd = 0d; // Différence entre le net à créditer et le montant DECIMAL 19 - 4
	private Double tcai4 = 1d; // Taux de change entre la devise des billets DECIMAL 15 - 7
	private String tope = HelperQuerry.padText("", HelperQuerry.RIGHT, 3, " "); // la télécompensation CHAR 3
	private String img = "N";
	private Date dsai = new Date(); // Date de saisie DATE
	private String hsai = new SimpleDateFormat("HH':'mm':'ss").format(new Date()); // Heure de saisie CHAR 12
	private String paysp = HelperQuerry.padText("", HelperQuerry.RIGHT, 3, " "); // Code pays du remettant et du porteur CHAR 3
	private String pdelp = HelperQuerry.padText("", HelperQuerry.RIGHT, 30, " "); // Pièce délivrée par CHAR 30
	private String manda = "N"; // Mandataire (O/N) CHAR 1
	private String refdos = HelperQuerry.padText("", HelperQuerry.RIGHT, 1, " "); // Référence de la procuration VARCHAR 0 - 20
	private Double tchfr = 0d; // Taux de change francophone DECIMAL 15 - 7
	
	//@Column(name = "CFRA")
	private String cfra = "O";
	
	//@Column(name = "NEFF")
	private String neff = "        ";
	
	//@Column(name = "TEFF")
	private String teff = " ";
	
	@Column(name = "NIDNP")
	private String nidnp = "                    ";
	
	@Column(name = "FRAISDIFF1")
	private Double fraisdiff1 = null;
	
	@Column(name = "FRAISDIFF2")
	private Double fraisdiff2 = null;

	@Column(name = "VALIDE")
	private Boolean valide = Boolean.FALSE;
	
	/**
	 * Marqueur determinant si l'evenement n'a pas ete poste dans Delta pour cause de TFJ
	 */
	@Column(name = "SUSPEND_IN_TFJ")
	private Boolean suspendInTFJ = Boolean.FALSE;



	public TraitementImpots() {
		super();
	}



	public TraitementImpots(String agsa, String age, String ope, String eve,
			String typ, String ndos, String age1, String dev1, String ncp1,
			String suf1, String clc1, String cli1, String nom1, String ges1,
			String sen1, Double mht1, Double mon1, Date dva1, String exo1,
			Double sol1, Double indh1, Double inds1, String desa1,
			String desa2, String desa3, String desa4, String desa5,
			String age2, String dev2, String ncp2, String suf2, String clc2,
			String cli2, String nom2, String ges2, String sen2, Double mht2,
			Double mon2, Date dva2, Date din, String exo2, Double sol2,
			Double indh2, Double inds2, String desc1, String desc2,
			String desc3, String desc4, String desc5, String etab, String guib,
			String nome, String domi, String adb1, String adb2, String adb3,
			String vilb, String cpob, String cpay, String etabr, String guibr,
			String comb, String cleb, String nomb, Double mban, Date dvab,
			String cai1, String tyc1, String dcai1, String scai1, Double mcai1,
			Double arrc1, String cai2, String tyc2, String dcai2, String scai2,
			Double mcai2, Double arrc2, String dev, Double mht, Double mnat,
			Double mbor, String nbor, Integer nblig, Double tcai2,
			Double tcai3, String nat, String nato, String opeo, String eveo,
			String pieo, Date dou, Date dco, String eta, String etap,
			Integer nbv, Integer nval, String uti, String utf, String uta,
			Double moa, Double mof, String lib1, String lib2, String lib3,
			String lib4, String lib5, String lib6, String lib7, String lib8,
			String lib9, String lib10, String agec, String agep, String intr,
			String orig, String rlet, String catr, String ceb, String plb,
			String cco, Date dret, String natp, String nump, String datp,
			String nomp, String ad1p, String ad2p, String delp, String serie,
			String nche, String chql, String chqc, String cab, String ncff,
			String csa, String cfra, String neff, String teff, Date dech,
			String tire, String agti, String agre, Integer nbji, String ptfc,
			String efav, String navl, String edom, String eopp, String efac,
			String moti, String envacc, String enom, String vicl, String teco,
			String tenv, String exjo, String org, String cai3, Double mcai3,
			String forc, String ocai3, Integer ncai3, String csp1, String csp2,
			String csp3, String csp4, String csp5, String ndom, String cmod,
			String devf, String ncpf, String suff, Double monf, Date dvaf,
			String exof, String agee, String deve, String ncpe, String sufe,
			String clce, String ncpi, String sufi, Double mimp, Date dvai,
			String ncpp, String sufp, Double prga, Double mrga, String term,
			String tvar, String intp, String cap, String prll, String ano,
			String etab1, String guib1, String com1b, String etab2,
			String guib2, String com2b, Double tcom1, Double mcom1,
			Double tcom2, Double mcom2, Double tcom3, Double mcom3,
			Double frai1, Double frai2, Double frai3, Double ttax1,
			Double mtax1, Double ttax2, Double mtax2, Double ttax3,
			Double mtax3, Double mnt1, Double mnt2, Double mnt3, Double mnt4,
			Double mnt5, String tyc3, String dcai3, String scai3, Double arrc3,
			Double mhtd, Double tcai4, String tope, String img, Date dsai,
			String hsai, String paysp, String pdelp, String manda,
			String refdos, Double tchfr, String nidnp, Double fraisdiff1,
			Double fraisdiff2) {
		super();
		this.agsa = agsa;
		this.age = age;
		this.ope = ope;
		this.eve = eve;
		this.typ = typ;
		this.ndos = ndos;
		this.age1 = age1;
		this.dev1 = dev1;
		this.ncp1 = ncp1;
		this.suf1 = suf1;
		this.clc1 = clc1;
		this.cli1 = cli1;
		this.nom1 = nom1;
		this.ges1 = ges1;
		this.sen1 = sen1;
		this.mht1 = mht1;
		this.mon1 = mon1;
		this.dva1 = dva1;
		this.exo1 = exo1;
		this.sol1 = sol1;
		this.indh1 = indh1;
		this.inds1 = inds1;
		this.desa1 = desa1;
		this.desa2 = desa2;
		this.desa3 = desa3;
		this.desa4 = desa4;
		this.desa5 = desa5;
		this.age2 = age2;
		this.dev2 = dev2;
		this.ncp2 = ncp2;
		this.suf2 = suf2;
		this.clc2 = clc2;
		this.cli2 = cli2;
		this.nom2 = nom2;
		this.ges2 = ges2;
		this.sen2 = sen2;
		this.mht2 = mht2;
		this.mon2 = mon2;
		this.dva2 = dva2;
		this.din = din;
		this.exo2 = exo2;
		this.sol2 = sol2;
		this.indh2 = indh2;
		this.inds2 = inds2;
		this.desc1 = desc1;
		this.desc2 = desc2;
		this.desc3 = desc3;
		this.desc4 = desc4;
		this.desc5 = desc5;
		this.etab = etab;
		this.guib = guib;
		this.nome = nome;
		this.domi = domi;
		this.adb1 = adb1;
		this.adb2 = adb2;
		this.adb3 = adb3;
		this.vilb = vilb;
		this.cpob = cpob;
		this.cpay = cpay;
		this.etabr = etabr;
		this.guibr = guibr;
		this.comb = comb;
		this.cleb = cleb;
		this.nomb = nomb;
		this.mban = mban;
		this.dvab = dvab;
		this.cai1 = cai1;
		this.tyc1 = tyc1;
		this.dcai1 = dcai1;
		this.scai1 = scai1;
		this.mcai1 = mcai1;
		this.arrc1 = arrc1;
		this.cai2 = cai2;
		this.tyc2 = tyc2;
		this.dcai2 = dcai2;
		this.scai2 = scai2;
		this.mcai2 = mcai2;
		this.arrc2 = arrc2;
		this.dev = dev;
		this.mht = mht;
		this.mnat = mnat;
		this.mbor = mbor;
		this.nbor = nbor;
		this.nblig = nblig;
		this.tcai2 = tcai2;
		this.tcai3 = tcai3;
		this.nat = nat;
		this.nato = nato;
		this.opeo = opeo;
		this.eveo = eveo;
		this.pieo = pieo;
		this.dou = dou;
		this.dco = dco;
		this.eta = eta;
		this.etap = etap;
		this.nbv = nbv;
		this.nval = nval;
		this.uti = uti;
		this.utf = utf;
		this.uta = uta;
		this.moa = moa;
		this.mof = mof;
		this.lib1 = lib1;
		this.lib2 = lib2;
		this.lib3 = lib3;
		this.lib4 = lib4;
		this.lib5 = lib5;
		this.lib6 = lib6;
		this.lib7 = lib7;
		this.lib8 = lib8;
		this.lib9 = lib9;
		this.lib10 = lib10;
		this.agec = agec;
		this.agep = agep;
		this.intr = intr;
		this.orig = orig;
		this.rlet = rlet;
		this.catr = catr;
		this.ceb = ceb;
		this.plb = plb;
		this.cco = cco;
		this.dret = dret;
		this.natp = natp;
		this.nump = nump;
		this.datp = datp;
		this.nomp = nomp;
		this.ad1p = ad1p;
		this.ad2p = ad2p;
		this.delp = delp;
		this.serie = serie;
		this.nche = nche;
		this.chql = chql;
		this.chqc = chqc;
		this.cab = cab;
		this.ncff = ncff;
		this.csa = csa;
		this.cfra = cfra;
		this.neff = neff;
		this.teff = teff;
		this.dech = dech;
		this.tire = tire;
		this.agti = agti;
		this.agre = agre;
		this.nbji = nbji;
		this.ptfc = ptfc;
		this.efav = efav;
		this.navl = navl;
		this.edom = edom;
		this.eopp = eopp;
		this.efac = efac;
		this.moti = moti;
		this.envacc = envacc;
		this.enom = enom;
		this.vicl = vicl;
		this.teco = teco;
		this.tenv = tenv;
		this.exjo = exjo;
		this.org = org;
		this.cai3 = cai3;
		this.mcai3 = mcai3;
		this.forc = forc;
		this.ocai3 = ocai3;
		this.ncai3 = ncai3;
		this.csp1 = csp1;
		this.csp2 = csp2;
		this.csp3 = csp3;
		this.csp4 = csp4;
		this.csp5 = csp5;
		this.ndom = ndom;
		this.cmod = cmod;
		this.devf = devf;
		this.ncpf = ncpf;
		this.suff = suff;
		this.monf = monf;
		this.dvaf = dvaf;
		this.exof = exof;
		this.agee = agee;
		this.deve = deve;
		this.ncpe = ncpe;
		this.sufe = sufe;
		this.clce = clce;
		this.ncpi = ncpi;
		this.sufi = sufi;
		this.mimp = mimp;
		this.dvai = dvai;
		this.ncpp = ncpp;
		this.sufp = sufp;
		this.prga = prga;
		this.mrga = mrga;
		this.term = term;
		this.tvar = tvar;
		this.intp = intp;
		this.cap = cap;
		this.prll = prll;
		this.ano = ano;
		this.etab1 = etab1;
		this.guib1 = guib1;
		this.com1b = com1b;
		this.etab2 = etab2;
		this.guib2 = guib2;
		this.com2b = com2b;
		this.tcom1 = tcom1;
		this.mcom1 = mcom1;
		this.tcom2 = tcom2;
		this.mcom2 = mcom2;
		this.tcom3 = tcom3;
		this.mcom3 = mcom3;
		this.frai1 = frai1;
		this.frai2 = frai2;
		this.frai3 = frai3;
		this.ttax1 = ttax1;
		this.mtax1 = mtax1;
		this.ttax2 = ttax2;
		this.mtax2 = mtax2;
		this.ttax3 = ttax3;
		this.mtax3 = mtax3;
		this.mnt1 = mnt1;
		this.mnt2 = mnt2;
		this.mnt3 = mnt3;
		this.mnt4 = mnt4;
		this.mnt5 = mnt5;
		this.tyc3 = tyc3;
		this.dcai3 = dcai3;
		this.scai3 = scai3;
		this.arrc3 = arrc3;
		this.mhtd = mhtd;
		this.tcai4 = tcai4;
		this.tope = tope;
		this.img = img;
		this.dsai = dsai;
		this.hsai = hsai;
		this.paysp = paysp;
		this.pdelp = pdelp;
		this.manda = manda;
		this.refdos = refdos;
		this.tchfr = tchfr;
		this.nidnp = nidnp;
		this.fraisdiff1 = fraisdiff1;
		this.fraisdiff2 = fraisdiff2;
	}
	
	
	/**
	 * Retourne la requete de creation de l'evenement
	 * @return
	 */
	public String getSaveQuery() {
		return "INSERT INTO BKEVE (AGSA, AGE,   OPE, EVE, TYP,   NDOS, AGE1, DEV1,   NCP1, SUF1, CLC1, CLI1, NOM1, GES1, SEN1, MHT1, MON1, " + 
		"DVA1, EXO1, SOL1, INDH1, INDS1, DESA1, DESA2, DESA3, DESA4, DESA5, AGE2, DEV2, NCP2, SUF2, CLC2, CLI2, NOM2, GES2,SEN2, MHT2, MON2, " +
		"DVA2, DIN, EXO2, SOL2, INDH2, INDS2, DESC1, DESC2, DESC3, DESC4, DESC5, ETAB, GUIB, NOME, DOMI, ADB1, ADB2, ADB3, VILB, CPOB, CPAY, " +
		"ETABR, GUIBR, COMB, CLEB, NOMB, MBAN, DVAB, CAI1, TYC1, DCAI1, SCAI1, MCAI1, ARRC1, CAI2, TYC2, DCAI2, SCAI2, MCAI2, ARRC2, DEV, MHT, " +
		"MNAT, MBOR, NBOR, NBLIG, TCAI2, TCAI3, NAT, NATO, OPEO, EVEO, PIEO, DOU, DCO, ETA, ETAP, NBV, NVAL, UTI, UTF, UTA, MOA, MOF, LIB1, LIB2, " +
		"LIB3, LIB4, LIB5, LIB6, LIB7, LIB8, LIB9, LIB10, AGEC, AGEP, INTR, ORIG, RLET, CATR, CEB, PLB, CCO, DRET, NATP, NUMP, DATP, NOMP, AD1P, AD2P, " +
		"DELP, SERIE, NCHE, CHQL, CHQC, CAB, NCFF, CSA,CFRA, NEFF, TEFF, DECH, TIRE, AGTI, AGRE, NBJI, PTFC, EFAV, NAVL, EDOM, EOPP, EFAC, MOTI, ENVACC, ENOM, VICL, TECO, " +
		"TENV, EXJO, ORG, CAI3, MCAI3, FORC, OCAI3, NCAI3, CSP1, CSP2, CSP3, CSP4, CSP5, NDOM, CMOD, DEVF, NCPF, SUFF, MONF, DVAF, EXOF, AGEE, DEVE, NCPE, " +
		"SUFE, CLCE, NCPI, SUFI, MIMP, DVAI, NCPP, SUFP, PRGA, MRGA, TERM, TVAR, INTP, CAP, PRLL, ANO, ETAB1, GUIB1, COM1B, ETAB2, GUIB2, COM2B, TCOM1, MCOM1," + 
		"TCOM2, MCOM2, TCOM3, MCOM3, FRAI1, FRAI2, FRAI3, TTAX1, MTAX1, TTAX2, MTAX2, TTAX3, MTAX3, MNT1, MNT2, MNT3, MNT4, MNT5, TYC3, DCAI3, SCAI3," + 
		"ARRC3, MHTD, TCAI4, TOPE, IMG, DSAI, HSAI, PAYSP, PDELP, MANDA, REFDOS, TCHFR)" +
		"VALUES (?,  ?, ?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?," + 
		"?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?, " +
		"?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?, " +
		"?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?," + 
		"?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?," + 
		"?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?, ?,  ?,  ?, ?,  ?, ?, ?,  ?, ? )";
	}

	public String getCheckQuery(){
		return "select * from BKEVE where OPE=? and EVE=? and AGE1=? and NCP1=? and CLC1=? and MON1=? and AGE2=? and NCP2=? and CLC2=? and MON2=? and DCO=? and ETA=?";		
	}

	public Object[] getQueryCheckValues() {

		Object[] values = new Object[12];
		values[0] = ope; 
		values[1] = eve; 
		values[2] = age1;
		values[3] = ncp1;
		values[4] = clc1;
		values[5] = mon1;
		values[6] = age2; 
		values[7] = ncp2; 
		values[8] = clc2; 
		values[9] = mon2 ;
		values[10] = dco;
		values[11] = eta;
		return values;

	}


	public Object[] getQueryValues() {

		Object[] values = new Object[236];

		values[0] = agsa;
		values[1] = age; 
		values[2] = ope; 
		values[3] = eve; 
		values[4] = typ; 
		values[5] = ndos;
		values[6] = age1;
		values[7] = dev1;
		values[8] = ncp1;
		values[9] = suf1;
		values[10] = clc1;
		values[11] = cli1;
		values[12] = nom1;
		values[13] = ges1;
		values[14] = sen1;
		values[15] = mht1;
		values[16] = mon1;
		values[17] = dva1;
		values[18] = exo1;
		values[19] = sol1;
		values[20] = indh1;
		values[21] = inds1;
		values[22] = desa1;
		values[23] = desa2;
		values[24] = desa3;
		values[25] = desa4;
		values[26] = desa5;
		values[27] = age2; 
		values[28] = dev2; 
		values[29] = ncp2; 
		values[30] = suf2;
		values[31] = clc2; 
		values[32] = cli2; 
		values[33] = nom2; 
		values[34] = ges2;
		values[35] = sen2;
		values[36] = mht2;
		values[37] = mon2 ;
		values[38] = dva2;
		values[39] = din;
		values[40] = exo2;
		values[41] = sol2;
		values[42] = indh2;
		values[43] = inds2;
		values[44] = desc1;
		values[45] = desc2;
		values[46] = desc3;
		values[47] = desc4;
		values[48] = desc5;
		values[49] = etab;
		values[50] = guib;
		values[51] = nome;
		values[52] = domi;
		values[53] = adb1;
		values[54] = adb2;
		values[55] = adb3;
		values[56] = vilb;
		values[57] = cpob;
		values[58] = cpay;
		values[59] = etabr;
		values[60] = guibr;
		values[61] = comb; 
		values[62] = cleb;
		values[63] = nomb;
		values[64] = mban;
		values[65] = dvab;
		values[66] = cai1;
		values[67] = tyc1;
		values[68] = dcai1;
		values[69] = scai1;
		values[70] = mcai1;
		values[71] = arrc1;
		values[72] = cai2;
		values[73] = tyc2;
		values[74] = dcai2;
		values[75] = scai2;
		values[76] = mcai2;
		values[77] = arrc2;
		values[78] = dev;
		values[79] = mht;
		values[80] = mnat;
		values[81] = mbor;
		values[82] = nbor;
		values[83] = nblig;
		values[84] = tcai2;
		values[85] = tcai3;
		values[86] = nat;
		values[87] = nato;
		values[88] = opeo;
		values[89] = eveo;
		values[90] = pieo;
		values[91] = dou;
		values[92] = dco;
		values[93] = eta;
		values[94] = etap;
		values[95] = nbv;
		values[96] = nval;
		values[97] = uti;
		values[98] = utf;
		values[99] = uta;
		values[100] = moa;
		values[101] = mof;
		values[102] = lib1;
		values[103] = lib2;
		values[104] = lib3;
		values[105] = lib4;
		values[106] = lib5;
		values[107] = lib6;
		values[108] = lib7;
		values[109] = lib8;
		values[110] = lib9; 
		values[111] = lib10;
		values[112] = agec;
		values[113] = agep;
		values[114] = intr;
		values[115] = orig;
		values[116] = rlet;
		values[117] = catr;
		values[118] = ceb; 
		values[119] = plb; 
		values[120] = cco; 
		values[121] = dret;
		values[122] = natp;
		values[123] = nump;
		values[124] = datp;
		values[125] = nomp;
		values[126] = ad1p;
		values[127] = ad2p;
		values[128] = delp;
		values[129] = serie;
		values[130] = nche;
		values[131] = chql;
		values[132] = chqc;
		values[133] = cab; 
		values[134] = ncff;
		values[135] = csa; 
		values[136] = cfra; 
		values[137] = neff;
		values[138] = teff; 
		values[139] = dech;
		values[140] = tire;
		values[141] = agti;
		values[142] = agre;
		values[143] = nbji ;
		values[144] = ptfc;
		values[145] = efav;
		values[146] = navl;
		values[147] = edom;
		values[148] = eopp;
		values[149] = efac;
		values[150] = moti;
		values[151] = envacc;
		values[152] = enom;
		values[153] = vicl;
		values[154] = teco;
		values[155] = tenv;
		values[156] = exjo;
		values[157] = org;
		values[158] = cai3;
		values[159] = mcai3;
		values[160] = forc;
		values[161] = ocai3;
		values[162] = ncai3;
		values[163] = csp1;
		values[164] = csp2;
		values[165] = csp3;
		values[166] = csp4;
		values[167] = csp5;
		values[168] = ndom;
		values[169] = cmod;
		values[170] = devf;
		values[171] = ncpf;
		values[172] = suff;
		values[173] = monf;
		values[174] = dvaf;
		values[175] = exof;
		values[176] = agee;
		values[177] = deve;
		values[178] = ncpe;
		values[179] = sufe;
		values[180] = clce;
		values[181] = ncpi;
		values[182] = sufi;
		values[183] = mimp;
		values[184] = dvai;
		values[185] = ncpp;
		values[186] = sufp;
		values[187] = prga;
		values[188] = mrga;
		values[189] = term;
		values[190] = tvar;
		values[191] = intp;
		values[192] = cap;
		values[193] = prll;
		values[194] = ano;
		values[195] = etab1;
		values[196] = guib1;
		values[197] = com1b;
		values[198] = etab2;
		values[199] = guib2;
		values[200] = com2b;
		values[201] = tcom1;
		values[202] = mcom1;
		values[203] = tcom2;
		values[204] = mcom2;
		values[205] = tcom3;
		values[206] = mcom3;
		values[207] = frai1;
		values[208] = frai2;
		values[209] = frai3;
		values[210] = ttax1;
		values[211] = mtax1;
		values[212] = ttax2;
		values[213] = mtax2;
		values[214] = ttax3;
		values[215] = mtax3;
		values[216] = mnt1;
		values[217] = mnt2;
		values[218] = mnt3;
		values[219] = mnt4;
		values[220] = mnt5;
		values[221] = tyc3;
		values[222] = dcai3;
		values[223] = scai3;
		values[224] = arrc3;
		values[225] = mhtd;
		values[226] = tcai4;
		values[227] = tope;
		values[228] = img;
		values[229] = dsai;
		values[230] = hsai;
		values[231] = paysp;
		values[232] = pdelp;
		values[233] = manda;
		values[234] = refdos;
		values[235] = tchfr;

		return values;

	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Date getDateTraitement() {
		return dateTraitement;
	}



	public void setDateTraitement(Date dateTraitement) {
		this.dateTraitement = dateTraitement;
	}



	public String getUtiPortal() {
		return utiPortal;
	}



	public void setUtiPortal(String utiPortal) {
		this.utiPortal = utiPortal;
	}



	public String getAgsa() {
		return agsa;
	}



	public void setAgsa(String agsa) {
		this.agsa = agsa;
	}



	public String getAge() {
		return age;
	}



	public void setAge(String age) {
		this.age = age;
	}



	public String getOpe() {
		return ope;
	}



	public void setOpe(String ope) {
		this.ope = ope;
	}



	public String getEve() {
		return eve;
	}



	public void setEve(String eve) {
		this.eve = eve;
	}



	public String getTyp() {
		return typ;
	}



	public void setTyp(String typ) {
		this.typ = typ;
	}



	public String getNdos() {
		return ndos;
	}



	public void setNdos(String ndos) {
		this.ndos = ndos;
	}



	public String getAge1() {
		return age1;
	}



	public void setAge1(String age1) {
		this.age1 = age1;
	}



	public String getDev1() {
		return dev1;
	}



	public void setDev1(String dev1) {
		this.dev1 = dev1;
	}



	public String getNcp1() {
		return ncp1;
	}



	public void setNcp1(String ncp1) {
		this.ncp1 = ncp1;
	}



	public String getSuf1() {
		return suf1;
	}



	public void setSuf1(String suf1) {
		this.suf1 = suf1;
	}



	public String getClc1() {
		return clc1;
	}



	public void setClc1(String clc1) {
		this.clc1 = clc1;
	}



	public String getCli1() {
		return cli1;
	}



	public void setCli1(String cli1) {
		this.cli1 = cli1;
	}



	public String getNom1() {
		return nom1;
	}



	public void setNom1(String nom1) {
		this.nom1 = nom1;
	}



	public String getGes1() {
		return ges1;
	}



	public void setGes1(String ges1) {
		this.ges1 = ges1;
	}



	public String getSen1() {
		return sen1;
	}



	public void setSen1(String sen1) {
		this.sen1 = sen1;
	}



	public Double getMht1() {
		return mht1;
	}



	public void setMht1(Double mht1) {
		this.mht1 = mht1;
	}



	public Double getMon1() {
		return mon1;
	}



	public void setMon1(Double mon1) {
		this.mon1 = mon1;
	}



	public Date getDva1() {
		return dva1;
	}



	public void setDva1(Date dva1) {
		this.dva1 = dva1;
	}



	public String getExo1() {
		return exo1;
	}



	public void setExo1(String exo1) {
		this.exo1 = exo1;
	}



	public Double getSol1() {
		return sol1;
	}



	public void setSol1(Double sol1) {
		this.sol1 = sol1;
	}



	public Double getIndh1() {
		return indh1;
	}



	public void setIndh1(Double indh1) {
		this.indh1 = indh1;
	}



	public Double getInds1() {
		return inds1;
	}



	public void setInds1(Double inds1) {
		this.inds1 = inds1;
	}



	public String getDesa1() {
		return desa1;
	}



	public void setDesa1(String desa1) {
		this.desa1 = desa1;
	}



	public String getDesa2() {
		return desa2;
	}



	public void setDesa2(String desa2) {
		this.desa2 = desa2;
	}



	public String getDesa3() {
		return desa3;
	}



	public void setDesa3(String desa3) {
		this.desa3 = desa3;
	}



	public String getDesa4() {
		return desa4;
	}



	public void setDesa4(String desa4) {
		this.desa4 = desa4;
	}



	public String getDesa5() {
		return desa5;
	}



	public void setDesa5(String desa5) {
		this.desa5 = desa5;
	}



	public String getAge2() {
		return age2;
	}



	public void setAge2(String age2) {
		this.age2 = age2;
	}



	public String getDev2() {
		return dev2;
	}



	public void setDev2(String dev2) {
		this.dev2 = dev2;
	}



	public String getNcp2() {
		return ncp2;
	}



	public void setNcp2(String ncp2) {
		this.ncp2 = ncp2;
	}



	public String getSuf2() {
		return suf2;
	}



	public void setSuf2(String suf2) {
		this.suf2 = suf2;
	}



	public String getClc2() {
		return clc2;
	}



	public void setClc2(String clc2) {
		this.clc2 = clc2;
	}



	public String getCli2() {
		return cli2;
	}



	public void setCli2(String cli2) {
		this.cli2 = cli2;
	}



	public String getNom2() {
		return nom2;
	}



	public void setNom2(String nom2) {
		this.nom2 = nom2;
	}



	public String getGes2() {
		return ges2;
	}



	public void setGes2(String ges2) {
		this.ges2 = ges2;
	}



	public String getSen2() {
		return sen2;
	}



	public void setSen2(String sen2) {
		this.sen2 = sen2;
	}



	public Double getMht2() {
		return mht2;
	}



	public void setMht2(Double mht2) {
		this.mht2 = mht2;
	}



	public Double getMon2() {
		return mon2;
	}



	public void setMon2(Double mon2) {
		this.mon2 = mon2;
	}



	public Date getDva2() {
		return dva2;
	}



	public void setDva2(Date dva2) {
		this.dva2 = dva2;
	}



	public Date getDin() {
		return din;
	}



	public void setDin(Date din) {
		this.din = din;
	}



	public String getExo2() {
		return exo2;
	}



	public void setExo2(String exo2) {
		this.exo2 = exo2;
	}



	public Double getSol2() {
		return sol2;
	}



	public void setSol2(Double sol2) {
		this.sol2 = sol2;
	}



	public Double getIndh2() {
		return indh2;
	}



	public void setIndh2(Double indh2) {
		this.indh2 = indh2;
	}



	public Double getInds2() {
		return inds2;
	}



	public void setInds2(Double inds2) {
		this.inds2 = inds2;
	}



	public String getDesc1() {
		return desc1;
	}



	public void setDesc1(String desc1) {
		this.desc1 = desc1;
	}



	public String getDesc2() {
		return desc2;
	}



	public void setDesc2(String desc2) {
		this.desc2 = desc2;
	}



	public String getDesc3() {
		return desc3;
	}



	public void setDesc3(String desc3) {
		this.desc3 = desc3;
	}



	public String getDesc4() {
		return desc4;
	}



	public void setDesc4(String desc4) {
		this.desc4 = desc4;
	}



	public String getDesc5() {
		return desc5;
	}



	public void setDesc5(String desc5) {
		this.desc5 = desc5;
	}



	public String getEtab() {
		return etab;
	}



	public void setEtab(String etab) {
		this.etab = etab;
	}



	public String getGuib() {
		return guib;
	}



	public void setGuib(String guib) {
		this.guib = guib;
	}



	public String getNome() {
		return nome;
	}



	public void setNome(String nome) {
		this.nome = nome;
	}



	public String getDomi() {
		return domi;
	}



	public void setDomi(String domi) {
		this.domi = domi;
	}



	public String getAdb1() {
		return adb1;
	}



	public void setAdb1(String adb1) {
		this.adb1 = adb1;
	}



	public String getAdb2() {
		return adb2;
	}



	public void setAdb2(String adb2) {
		this.adb2 = adb2;
	}



	public String getAdb3() {
		return adb3;
	}



	public void setAdb3(String adb3) {
		this.adb3 = adb3;
	}



	public String getVilb() {
		return vilb;
	}



	public void setVilb(String vilb) {
		this.vilb = vilb;
	}



	public String getCpob() {
		return cpob;
	}



	public void setCpob(String cpob) {
		this.cpob = cpob;
	}



	public String getCpay() {
		return cpay;
	}



	public void setCpay(String cpay) {
		this.cpay = cpay;
	}



	public String getEtabr() {
		return etabr;
	}



	public void setEtabr(String etabr) {
		this.etabr = etabr;
	}



	public String getGuibr() {
		return guibr;
	}



	public void setGuibr(String guibr) {
		this.guibr = guibr;
	}



	public String getComb() {
		return comb;
	}



	public void setComb(String comb) {
		this.comb = comb;
	}



	public String getCleb() {
		return cleb;
	}



	public void setCleb(String cleb) {
		this.cleb = cleb;
	}



	public String getNomb() {
		return nomb;
	}



	public void setNomb(String nomb) {
		this.nomb = nomb;
	}



	public Double getMban() {
		return mban;
	}



	public void setMban(Double mban) {
		this.mban = mban;
	}



	public Date getDvab() {
		return dvab;
	}



	public void setDvab(Date dvab) {
		this.dvab = dvab;
	}



	public String getCai1() {
		return cai1;
	}



	public void setCai1(String cai1) {
		this.cai1 = cai1;
	}



	public String getTyc1() {
		return tyc1;
	}



	public void setTyc1(String tyc1) {
		this.tyc1 = tyc1;
	}



	public String getDcai1() {
		return dcai1;
	}



	public void setDcai1(String dcai1) {
		this.dcai1 = dcai1;
	}



	public String getScai1() {
		return scai1;
	}



	public void setScai1(String scai1) {
		this.scai1 = scai1;
	}



	public Double getMcai1() {
		return mcai1;
	}



	public void setMcai1(Double mcai1) {
		this.mcai1 = mcai1;
	}



	public Double getArrc1() {
		return arrc1;
	}



	public void setArrc1(Double arrc1) {
		this.arrc1 = arrc1;
	}



	public String getCai2() {
		return cai2;
	}



	public void setCai2(String cai2) {
		this.cai2 = cai2;
	}



	public String getTyc2() {
		return tyc2;
	}



	public void setTyc2(String tyc2) {
		this.tyc2 = tyc2;
	}



	public String getDcai2() {
		return dcai2;
	}



	public void setDcai2(String dcai2) {
		this.dcai2 = dcai2;
	}



	public String getScai2() {
		return scai2;
	}



	public void setScai2(String scai2) {
		this.scai2 = scai2;
	}



	public Double getMcai2() {
		return mcai2;
	}



	public void setMcai2(Double mcai2) {
		this.mcai2 = mcai2;
	}



	public Double getArrc2() {
		return arrc2;
	}



	public void setArrc2(Double arrc2) {
		this.arrc2 = arrc2;
	}



	public String getDev() {
		return dev;
	}



	public void setDev(String dev) {
		this.dev = dev;
	}



	public Double getMht() {
		return mht;
	}



	public void setMht(Double mht) {
		this.mht = mht;
	}



	public Double getMnat() {
		return mnat;
	}



	public void setMnat(Double mnat) {
		this.mnat = mnat;
	}



	public Double getMbor() {
		return mbor;
	}



	public void setMbor(Double mbor) {
		this.mbor = mbor;
	}



	public String getNbor() {
		return nbor;
	}



	public void setNbor(String nbor) {
		this.nbor = nbor;
	}



	public Integer getNblig() {
		return nblig;
	}



	public void setNblig(Integer nblig) {
		this.nblig = nblig;
	}



	public Double getTcai2() {
		return tcai2;
	}



	public void setTcai2(Double tcai2) {
		this.tcai2 = tcai2;
	}



	public Double getTcai3() {
		return tcai3;
	}



	public void setTcai3(Double tcai3) {
		this.tcai3 = tcai3;
	}



	public String getNat() {
		return nat;
	}



	public void setNat(String nat) {
		this.nat = nat;
	}



	public String getNato() {
		return nato;
	}



	public void setNato(String nato) {
		this.nato = nato;
	}



	public String getOpeo() {
		return opeo;
	}



	public void setOpeo(String opeo) {
		this.opeo = opeo;
	}



	public String getEveo() {
		return eveo;
	}



	public void setEveo(String eveo) {
		this.eveo = eveo;
	}



	public String getPieo() {
		return pieo;
	}



	public void setPieo(String pieo) {
		this.pieo = pieo;
	}



	public Date getDou() {
		return dou;
	}



	public void setDou(Date dou) {
		this.dou = dou;
	}



	public Date getDco() {
		return dco;
	}



	public void setDco(Date dco) {
		this.dco = dco;
	}



	public String getEta() {
		return eta;
	}



	public void setEta(String eta) {
		this.eta = eta;
	}



	public String getEtap() {
		return etap;
	}



	public void setEtap(String etap) {
		this.etap = etap;
	}



	public Integer getNbv() {
		return nbv;
	}



	public void setNbv(Integer nbv) {
		this.nbv = nbv;
	}



	public Integer getNval() {
		return nval;
	}



	public void setNval(Integer nval) {
		this.nval = nval;
	}



	public String getUti() {
		return uti;
	}



	public void setUti(String uti) {
		this.uti = uti;
	}



	public String getUtf() {
		return utf;
	}



	public void setUtf(String utf) {
		this.utf = utf;
	}



	public String getUta() {
		return uta;
	}



	public void setUta(String uta) {
		this.uta = uta;
	}



	



	



	public String getLib1() {
		return lib1;
	}



	public void setLib1(String lib1) {
		this.lib1 = lib1;
	}



	public String getLib2() {
		return lib2;
	}



	public void setLib2(String lib2) {
		this.lib2 = lib2;
	}



	public String getLib3() {
		return lib3;
	}



	public void setLib3(String lib3) {
		this.lib3 = lib3;
	}



	public String getLib4() {
		return lib4;
	}



	public void setLib4(String lib4) {
		this.lib4 = lib4;
	}



	public String getLib5() {
		return lib5;
	}



	public void setLib5(String lib5) {
		this.lib5 = lib5;
	}



	public String getLib6() {
		return lib6;
	}



	public void setLib6(String lib6) {
		this.lib6 = lib6;
	}



	public String getLib7() {
		return lib7;
	}



	public void setLib7(String lib7) {
		this.lib7 = lib7;
	}



	public String getLib8() {
		return lib8;
	}



	public void setLib8(String lib8) {
		this.lib8 = lib8;
	}



	public String getLib9() {
		return lib9;
	}



	public void setLib9(String lib9) {
		this.lib9 = lib9;
	}



	public String getLib10() {
		return lib10;
	}



	public void setLib10(String lib10) {
		this.lib10 = lib10;
	}



	public String getAgec() {
		return agec;
	}



	public void setAgec(String agec) {
		this.agec = agec;
	}



	public String getAgep() {
		return agep;
	}



	public void setAgep(String agep) {
		this.agep = agep;
	}



	public String getIntr() {
		return intr;
	}



	public void setIntr(String intr) {
		this.intr = intr;
	}



	public String getOrig() {
		return orig;
	}



	public void setOrig(String orig) {
		this.orig = orig;
	}



	public String getRlet() {
		return rlet;
	}



	public void setRlet(String rlet) {
		this.rlet = rlet;
	}



	public String getCatr() {
		return catr;
	}



	public void setCatr(String catr) {
		this.catr = catr;
	}



	public String getCeb() {
		return ceb;
	}



	public void setCeb(String ceb) {
		this.ceb = ceb;
	}



	public String getPlb() {
		return plb;
	}



	public void setPlb(String plb) {
		this.plb = plb;
	}


	public Double getMoa() {
		return moa;
	}



	public void setMoa(Double moa) {
		this.moa = moa;
	}



	public Double getMof() {
		return mof;
	}



	public void setMof(Double mof) {
		this.mof = mof;
	}



	public String getCco() {
		return cco;
	}



	public void setCco(String cco) {
		this.cco = cco;
	}



	public String getDatp() {
		return datp;
	}



	public void setDatp(String datp) {
		this.datp = datp;
	}



	public Date getDret() {
		return dret;
	}



	public void setDret(Date dret) {
		this.dret = dret;
	}



	public String getNatp() {
		return natp;
	}



	public void setNatp(String natp) {
		this.natp = natp;
	}



	public String getNump() {
		return nump;
	}



	public void setNump(String nump) {
		this.nump = nump;
	}


	public String getNomp() {
		return nomp;
	}



	public void setNomp(String nomp) {
		this.nomp = nomp;
	}



	public String getAd1p() {
		return ad1p;
	}



	public void setAd1p(String ad1p) {
		this.ad1p = ad1p;
	}



	public String getAd2p() {
		return ad2p;
	}



	public void setAd2p(String ad2p) {
		this.ad2p = ad2p;
	}



	public String getDelp() {
		return delp;
	}



	public void setDelp(String delp) {
		this.delp = delp;
	}



	public String getSerie() {
		return serie;
	}



	public void setSerie(String serie) {
		this.serie = serie;
	}



	public String getNche() {
		return nche;
	}



	public void setNche(String nche) {
		this.nche = nche;
	}



	public String getChql() {
		return chql;
	}



	public void setChql(String chql) {
		this.chql = chql;
	}



	public String getChqc() {
		return chqc;
	}



	public void setChqc(String chqc) {
		this.chqc = chqc;
	}



	public String getCab() {
		return cab;
	}



	public void setCab(String cab) {
		this.cab = cab;
	}



	public String getNcff() {
		return ncff;
	}



	public void setNcff(String ncff) {
		this.ncff = ncff;
	}



	public String getCsa() {
		return csa;
	}



	public void setCsa(String csa) {
		this.csa = csa;
	}



	public String getCfra() {
		return cfra;
	}



	public void setCfra(String cfra) {
		this.cfra = cfra;
	}



	public String getNeff() {
		return neff;
	}



	public void setNeff(String neff) {
		this.neff = neff;
	}



	public String getTeff() {
		return teff;
	}



	public void setTeff(String teff) {
		this.teff = teff;
	}



	public Date getDech() {
		return dech;
	}



	public void setDech(Date dech) {
		this.dech = dech;
	}



	public String getTire() {
		return tire;
	}



	public void setTire(String tire) {
		this.tire = tire;
	}



	public String getAgti() {
		return agti;
	}



	public void setAgti(String agti) {
		this.agti = agti;
	}



	public String getAgre() {
		return agre;
	}



	public void setAgre(String agre) {
		this.agre = agre;
	}



	public Integer getNbji() {
		return nbji;
	}



	public void setNbji(Integer nbji) {
		this.nbji = nbji;
	}



	public String getPtfc() {
		return ptfc;
	}



	public void setPtfc(String ptfc) {
		this.ptfc = ptfc;
	}



	public String getEfav() {
		return efav;
	}



	public void setEfav(String efav) {
		this.efav = efav;
	}



	public String getNavl() {
		return navl;
	}



	public void setNavl(String navl) {
		this.navl = navl;
	}



	public String getEdom() {
		return edom;
	}



	public void setEdom(String edom) {
		this.edom = edom;
	}



	public String getEopp() {
		return eopp;
	}



	public void setEopp(String eopp) {
		this.eopp = eopp;
	}



	public String getEfac() {
		return efac;
	}



	public void setEfac(String efac) {
		this.efac = efac;
	}



	public String getMoti() {
		return moti;
	}



	public void setMoti(String moti) {
		this.moti = moti;
	}



	public String getEnvacc() {
		return envacc;
	}



	public void setEnvacc(String envacc) {
		this.envacc = envacc;
	}



	public String getEnom() {
		return enom;
	}



	public void setEnom(String enom) {
		this.enom = enom;
	}



	public String getVicl() {
		return vicl;
	}



	public void setVicl(String vicl) {
		this.vicl = vicl;
	}



	public String getTeco() {
		return teco;
	}



	public void setTeco(String teco) {
		this.teco = teco;
	}



	public String getTenv() {
		return tenv;
	}



	public void setTenv(String tenv) {
		this.tenv = tenv;
	}



	public String getExjo() {
		return exjo;
	}



	public void setExjo(String exjo) {
		this.exjo = exjo;
	}



	public String getOrg() {
		return org;
	}



	public void setOrg(String org) {
		this.org = org;
	}



	public String getCai3() {
		return cai3;
	}



	public void setCai3(String cai3) {
		this.cai3 = cai3;
	}



	public Double getMcai3() {
		return mcai3;
	}



	public void setMcai3(Double mcai3) {
		this.mcai3 = mcai3;
	}



	public String getForc() {
		return forc;
	}



	public void setForc(String forc) {
		this.forc = forc;
	}



	public String getOcai3() {
		return ocai3;
	}



	public void setOcai3(String ocai3) {
		this.ocai3 = ocai3;
	}



	public Integer getNcai3() {
		return ncai3;
	}



	public void setNcai3(Integer ncai3) {
		this.ncai3 = ncai3;
	}



	public String getCsp1() {
		return csp1;
	}



	public void setCsp1(String csp1) {
		this.csp1 = csp1;
	}



	public String getCsp2() {
		return csp2;
	}



	public void setCsp2(String csp2) {
		this.csp2 = csp2;
	}



	public String getCsp3() {
		return csp3;
	}



	public void setCsp3(String csp3) {
		this.csp3 = csp3;
	}



	public String getCsp4() {
		return csp4;
	}



	public void setCsp4(String csp4) {
		this.csp4 = csp4;
	}



	public String getCsp5() {
		return csp5;
	}



	public void setCsp5(String csp5) {
		this.csp5 = csp5;
	}



	public String getNdom() {
		return ndom;
	}



	public void setNdom(String ndom) {
		this.ndom = ndom;
	}



	public String getCmod() {
		return cmod;
	}



	public void setCmod(String cmod) {
		this.cmod = cmod;
	}



	public String getDevf() {
		return devf;
	}



	public void setDevf(String devf) {
		this.devf = devf;
	}



	public String getNcpf() {
		return ncpf;
	}



	public void setNcpf(String ncpf) {
		this.ncpf = ncpf;
	}



	public String getSuff() {
		return suff;
	}



	public void setSuff(String suff) {
		this.suff = suff;
	}



	public Double getMonf() {
		return monf;
	}



	public void setMonf(Double monf) {
		this.monf = monf;
	}



	public Date getDvaf() {
		return dvaf;
	}



	public void setDvaf(Date dvaf) {
		this.dvaf = dvaf;
	}



	public String getExof() {
		return exof;
	}



	public void setExof(String exof) {
		this.exof = exof;
	}



	public String getAgee() {
		return agee;
	}



	public void setAgee(String agee) {
		this.agee = agee;
	}



	public String getDeve() {
		return deve;
	}



	public void setDeve(String deve) {
		this.deve = deve;
	}



	public String getNcpe() {
		return ncpe;
	}



	public void setNcpe(String ncpe) {
		this.ncpe = ncpe;
	}



	public String getSufe() {
		return sufe;
	}



	public void setSufe(String sufe) {
		this.sufe = sufe;
	}



	public String getClce() {
		return clce;
	}



	public void setClce(String clce) {
		this.clce = clce;
	}



	public String getNcpi() {
		return ncpi;
	}



	public void setNcpi(String ncpi) {
		this.ncpi = ncpi;
	}



	public String getSufi() {
		return sufi;
	}



	public void setSufi(String sufi) {
		this.sufi = sufi;
	}



	public Double getMimp() {
		return mimp;
	}



	public void setMimp(Double mimp) {
		this.mimp = mimp;
	}



	public Date getDvai() {
		return dvai;
	}



	public void setDvai(Date dvai) {
		this.dvai = dvai;
	}



	public String getNcpp() {
		return ncpp;
	}



	public void setNcpp(String ncpp) {
		this.ncpp = ncpp;
	}



	public String getSufp() {
		return sufp;
	}



	public void setSufp(String sufp) {
		this.sufp = sufp;
	}



	public Double getPrga() {
		return prga;
	}



	public void setPrga(Double prga) {
		this.prga = prga;
	}



	public Double getMrga() {
		return mrga;
	}



	public void setMrga(Double mrga) {
		this.mrga = mrga;
	}



	public String getTerm() {
		return term;
	}



	public void setTerm(String term) {
		this.term = term;
	}



	public String getTvar() {
		return tvar;
	}



	public void setTvar(String tvar) {
		this.tvar = tvar;
	}



	public String getIntp() {
		return intp;
	}



	public void setIntp(String intp) {
		this.intp = intp;
	}



	public String getCap() {
		return cap;
	}



	public void setCap(String cap) {
		this.cap = cap;
	}



	public String getPrll() {
		return prll;
	}



	public void setPrll(String prll) {
		this.prll = prll;
	}



	public String getAno() {
		return ano;
	}



	public void setAno(String ano) {
		this.ano = ano;
	}



	public String getEtab1() {
		return etab1;
	}



	public void setEtab1(String etab1) {
		this.etab1 = etab1;
	}



	public String getGuib1() {
		return guib1;
	}



	public void setGuib1(String guib1) {
		this.guib1 = guib1;
	}



	public String getCom1b() {
		return com1b;
	}



	public void setCom1b(String com1b) {
		this.com1b = com1b;
	}



	public String getEtab2() {
		return etab2;
	}



	public void setEtab2(String etab2) {
		this.etab2 = etab2;
	}



	public String getGuib2() {
		return guib2;
	}



	public void setGuib2(String guib2) {
		this.guib2 = guib2;
	}



	public String getCom2b() {
		return com2b;
	}



	public void setCom2b(String com2b) {
		this.com2b = com2b;
	}



	public Double getTcom1() {
		return tcom1;
	}



	public void setTcom1(Double tcom1) {
		this.tcom1 = tcom1;
	}



	public Double getMcom1() {
		return mcom1;
	}



	public void setMcom1(Double mcom1) {
		this.mcom1 = mcom1;
	}



	public Double getTcom2() {
		return tcom2;
	}



	public void setTcom2(Double tcom2) {
		this.tcom2 = tcom2;
	}



	public Double getMcom2() {
		return mcom2;
	}



	public void setMcom2(Double mcom2) {
		this.mcom2 = mcom2;
	}



	public Double getTcom3() {
		return tcom3;
	}



	public void setTcom3(Double tcom3) {
		this.tcom3 = tcom3;
	}



	public Double getMcom3() {
		return mcom3;
	}



	public void setMcom3(Double mcom3) {
		this.mcom3 = mcom3;
	}



	public Double getFrai1() {
		return frai1;
	}



	public void setFrai1(Double frai1) {
		this.frai1 = frai1;
	}



	public Double getFrai2() {
		return frai2;
	}



	public void setFrai2(Double frai2) {
		this.frai2 = frai2;
	}



	public Double getFrai3() {
		return frai3;
	}



	public void setFrai3(Double frai3) {
		this.frai3 = frai3;
	}



	public Double getTtax1() {
		return ttax1;
	}



	public void setTtax1(Double ttax1) {
		this.ttax1 = ttax1;
	}



	public Double getMtax1() {
		return mtax1;
	}



	public void setMtax1(Double mtax1) {
		this.mtax1 = mtax1;
	}



	public Double getTtax2() {
		return ttax2;
	}



	public void setTtax2(Double ttax2) {
		this.ttax2 = ttax2;
	}



	public Double getMtax2() {
		return mtax2;
	}



	public void setMtax2(Double mtax2) {
		this.mtax2 = mtax2;
	}



	public Double getTtax3() {
		return ttax3;
	}



	public void setTtax3(Double ttax3) {
		this.ttax3 = ttax3;
	}



	public Double getMtax3() {
		return mtax3;
	}



	public void setMtax3(Double mtax3) {
		this.mtax3 = mtax3;
	}



	public Double getMnt1() {
		return mnt1;
	}



	public void setMnt1(Double mnt1) {
		this.mnt1 = mnt1;
	}



	public Double getMnt2() {
		return mnt2;
	}



	public void setMnt2(Double mnt2) {
		this.mnt2 = mnt2;
	}



	public Double getMnt3() {
		return mnt3;
	}



	public void setMnt3(Double mnt3) {
		this.mnt3 = mnt3;
	}



	public Double getMnt4() {
		return mnt4;
	}



	public void setMnt4(Double mnt4) {
		this.mnt4 = mnt4;
	}



	public Double getMnt5() {
		return mnt5;
	}



	public void setMnt5(Double mnt5) {
		this.mnt5 = mnt5;
	}



	public String getTyc3() {
		return tyc3;
	}



	public void setTyc3(String tyc3) {
		this.tyc3 = tyc3;
	}



	public String getDcai3() {
		return dcai3;
	}



	public void setDcai3(String dcai3) {
		this.dcai3 = dcai3;
	}



	public String getScai3() {
		return scai3;
	}



	public void setScai3(String scai3) {
		this.scai3 = scai3;
	}



	public Double getArrc3() {
		return arrc3;
	}



	public void setArrc3(Double arrc3) {
		this.arrc3 = arrc3;
	}



	public Double getMhtd() {
		return mhtd;
	}



	public void setMhtd(Double mhtd) {
		this.mhtd = mhtd;
	}



	public Double getTcai4() {
		return tcai4;
	}



	public void setTcai4(Double tcai4) {
		this.tcai4 = tcai4;
	}



	public String getTope() {
		return tope;
	}



	public void setTope(String tope) {
		this.tope = tope;
	}



	public String getImg() {
		return img;
	}



	public void setImg(String img) {
		this.img = img;
	}



	public Date getDsai() {
		return dsai;
	}



	public void setDsai(Date dsai) {
		this.dsai = dsai;
	}



	public String getHsai() {
		return hsai;
	}



	public void setHsai(String hsai) {
		this.hsai = hsai;
	}



	public String getPaysp() {
		return paysp;
	}



	public void setPaysp(String paysp) {
		this.paysp = paysp;
	}



	public String getPdelp() {
		return pdelp;
	}



	public void setPdelp(String pdelp) {
		this.pdelp = pdelp;
	}



	public String getManda() {
		return manda;
	}



	public void setManda(String manda) {
		this.manda = manda;
	}



	public String getRefdos() {
		return refdos;
	}



	public void setRefdos(String refdos) {
		this.refdos = refdos;
	}



	public Double getTchfr() {
		return tchfr;
	}



	public void setTchfr(Double tchfr) {
		this.tchfr = tchfr;
	}



	public String getNidnp() {
		return nidnp;
	}



	public void setNidnp(String nidnp) {
		this.nidnp = nidnp;
	}



	public Double getFraisdiff1() {
		return fraisdiff1;
	}



	public void setFraisdiff1(Double fraisdiff1) {
		this.fraisdiff1 = fraisdiff1;
	}



	public Double getFraisdiff2() {
		return fraisdiff2;
	}



	public void setFraisdiff2(Double fraisdiff2) {
		this.fraisdiff2 = fraisdiff2;
	}



	public Boolean getValide() {
		return valide;
	}



	public void setValide(Boolean valide) {
		this.valide = valide;
	}


}

