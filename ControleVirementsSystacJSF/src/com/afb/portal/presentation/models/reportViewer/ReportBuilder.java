package com.afb.portal.presentation.models.reportViewer;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashMap;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRCsvExporterParameter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.JRPrintImage;
import net.sf.jasperreports.engine.JasperPrintManager;









import com.afb.portal.presentation.tools.ExceptionHelper;
import com.afb.portal.presentation.tools.ViewHelper;
import com.aspose.words.jasperreports.AWDocExporter;

/**
 * Classe permettant de construire les etats
 * @author Francis K, Stéphane M
 * @version 1.0
 */
public class ReportBuilder {

	protected boolean information = false;
	protected boolean error = false;



	/**
	 * Methode de construction de l'etat
	 * @param reportName
	 * @param map
	 * @param maCollection
	 * @return
	 * @throws Exception
	 */
	private static JasperPrint printReport(String reportName, HashMap<Object, Object> map, Collection<?> maCollection) throws Exception {

		// Construction de la source de donnees de l'etat
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(maCollection);

		// Construction de l'etat
		return JasperFillManager.fillReport(reportName, map, dataSource);

	}

	/**
	 * Methode d'exportation de l'etat en fichier html
	 * @param reportName URL du fichier jasper 
	 * @param map Parametres de l'etat
	 * @param maCollection Source de donnees de l'etat
	 * @param destFileName Fichier de destination
	 * @throws Exception
	 */
	public static void exportReportToHtmlFile(String reportName, HashMap<Object, Object> map, Collection<?> maCollection, String destFileName) throws Exception {
		File sourceFile = new File(reportName);

		// Construction du JasperPrint
		JasperPrint jp = printReport(reportName, map, maCollection);


		// Export de l'etat vers le fichier html specifie
		JasperExportManager.exportReportToHtmlFile(jp, destFileName);
	}

	/**
	 * Methode d'exportation de l'etat dans un flux d'octets de sortie pour fichiers html
	 * @param reportName URL du fichier jasper 
	 * @param map Parametres de l'etat
	 * @param maCollection Source de donnees de l'etat
	 * @param os Flux de sortie
	 * @throws Exception
	 */
	public static byte[] getReportHtmlBytes(String reportName, HashMap<Object, Object> map, Collection<?> maCollection) throws Exception {

		// Initialisation du tableau de byte a retourner
		byte[] data = null;

		// Export de l'etat dans un fichier html
		exportReportToHtmlFile(reportName, map, maCollection, "report.html");

		// Si le fichier n'existe pas on sort
		if(!new File("report.html").exists()) return data;

		// Obtention d'un InputStream du le fichier html genere
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File("report.html")));

		// Initilisation d'un flux de sortie
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		// Initialisation d'un Byte
		int b;

		// Parcours du fichier et ecriture dans le flux de sortie
		while((b = bis.read()) != -1) baos.write(b);

		// On referme
		baos.flush();

		// Construction du tableau de byte a partir du flux de sortie
		data = baos.toByteArray();

		// Fermeture des flux
		bis.close();
		//baos.close();

		// Retourne le tableau de bytes
		return data;
	}

	/**
	 * Methode de generation d'un tableau d'octets de l'etat
	 * @param reportName URL du fichier jasper 
	 * @param map Parametres de l'etat
	 * @param maCollection Source de donnees de l'etat
	 * @return byte[]
	 * @throws Exception
	 */
	public static byte[] getReportPDFBytes(String reportName, HashMap<Object, Object> map, Collection<?> maCollection) throws Exception {
		System.out.println("*****Constructing PDF BYTES*****");
		// Construction du JasperPrint
		JasperPrint jp = printReport(reportName, map, maCollection);

		// Construction du tableau de bytes
		return JasperExportManager.exportReportToPdf(jp);
	}


	public static byte[] getReportExcelBytes(String reportName, HashMap<Object, Object> map, Collection<?> maCollection, String nameFile) throws Exception {

		byte[] rtfResume = null;
		File sourceFile = null;

		JRXlsxExporter exporter = null;

		//JRCsvExporter exporter = null;

		try{

			/**System.out.println("-----------map size-------" + map.size());

			for(Object s:map.keySet()){

				System.out.println("---value----" + s.toString());
				if(s.toString().equals("tauxConformiteGlobal")){
					System.out.println("----map.get(s)----" + map.get(s));
				}
			}

			sourceFile = new File(reportName);

			exporter = new JRXlsxExporter();
			//exporter = new JRCsvExporter();
			//final ByteArrayOutputStream rtfStream = new ByteArrayOutputStream();
			JasperPrint jp = printReport(reportName, map, maCollection);
			System.out.println("-----------arrived here1");

			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
			System.out.println("---------------arrived here2");

			File destFile = new File(sourceFile.getParent(), "statistiques_taux_de_conformite.xlsx");
			System.out.println("---------------arrived here3");

			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile.toString());
			System.out.println("---------------arrived here4");

			exporter.exportReport();
			System.out.println("---------------arrived here5a");

			//if(!new File("C://statistiques_voeux.xlsx").exists()) return rtfResume;

			// Obtention d'un InputStream du le fichier html genere
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(destFile.toString())));
			System.out.println("---------------arrived here5b");

			// Initilisation d'un flux de sortie
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			System.out.println("---------------arrived here5c");

			// Initialisation d'un Byte
			int b;

			// Parcours du fichier et ecriture dans le flux de sortie
			while((b = bis.read()) != -1) baos.write(b);
			System.out.println("---------------arrived here6");

			// On referme
			baos.flush();
			System.out.println("---------------arrived here7");

			// Construction du tableau de byte a partir du flux de sortie
			rtfResume = baos.toByteArray();
			System.out.println("---------------arrived here8");

			baos.close();
			System.out.println("---------------arrived here9");

			// Fermeture des flux
			bis.close();
			System.out.println("---------------arrived here10");
			//baos.close();*/

			sourceFile = new File(reportName);

			exporter = new JRXlsxExporter();
			//exporter = new JRCsvExporter();
			//final ByteArrayOutputStream rtfStream = new ByteArrayOutputStream();
			JasperPrint jp = printReport(reportName, map, maCollection);
			System.out.println("-----------arrived here1");

			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
			System.out.println("---------------arrived here2");

			File destFile = new File(sourceFile.getParent(), nameFile);
			System.out.println("----source file---" + sourceFile.getParent());
			System.out.println("---------------arrived here3");

			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile.toString());
			System.out.println("---------------arrived here4");

			exporter.exportReport();
			System.out.println("---------------arrived here5a");

			//if(!new File("C://statistiques_voeux.xlsx").exists()) return rtfResume;

			// Obtention d'un InputStream du le fichier html genere
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(destFile.toString())));
			System.out.println("---------------arrived here5b");

			// Initilisation d'un flux de sortie
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			System.out.println("---------------arrived here5c");

			// Initialisation d'un Byte
			int b;

			// Parcours du fichier et ecriture dans le flux de sortie
			while((b = bis.read()) != -1) baos.write(b);
			System.out.println("---------------arrived here6");

			// On referme
			baos.flush();
			System.out.println("---------------arrived here7");

			// Construction du tableau de byte a partir du flux de sortie
			rtfResume = baos.toByteArray();
			System.out.println("---------------arrived here8");

			baos.close();
			System.out.println("---------------arrived here9");

			// Fermeture des flux
			bis.close();
			System.out.println("---------------arrived here10");
			//baos.close();


		}
		catch(Exception e){

			e.printStackTrace();

			System.out.println("--------Error caught in catch--------");

			return null;
		}
		finally{

			exporter = null;

		}


		// Retourne le tableau de bytes
		return rtfResume;

	}




	public static byte[] getReportTextBytes(String reportName, HashMap<Object, Object> map, Collection<?> maCollection, String nameFile) throws Exception {

		byte[] rtfResume = null;
		File sourceFile = null;

		JRTextExporter exporter = null;


		try{

			sourceFile = new File(reportName);

			exporter = new JRTextExporter();
			//exporter = new JRCsvExporter();
			//final ByteArrayOutputStream rtfStream = new ByteArrayOutputStream();
			JasperPrint jp = printReport(reportName, map, maCollection);
			System.out.println("-----------arrived here1");

			//exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
			System.out.println("---------------arrived here2");

			exporter.setParameter(JRTextExporterParameter.JASPER_PRINT, jp);
			/**exporter.setParameter(JRTextExporterParameter.FIELD_DELIMITER, ",");
			    exporter.setParameter(JRTextExporterParameter.RECORD_DELIMITER,System.getProperty("line.separator"));
			    exporter.setParameter(JRTextExporterParameter.OUTPUT_STREAM, ouputStream);*/

			exporter.setParameter(JRTextExporterParameter.CHARACTER_WIDTH, 8);
			exporter.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT, 8);
			
			exporter.setParameter(JRTextExporterParameter.PAGE_WIDTH, 900);
			exporter.setParameter(JRTextExporterParameter.PAGE_HEIGHT, 765);

			File destFile = new File(sourceFile.getParent(), nameFile);
			System.out.println("----source file---" + sourceFile.getParent());
			System.out.println("---------------arrived here3");

			exporter.setParameter(JRTextExporterParameter.OUTPUT_FILE_NAME, destFile.toString());
			System.out.println("---------------arrived here4");

			exporter.exportReport();
			System.out.println("---------------arrived here5a");


			// Obtention d'un InputStream du le fichier html genere
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(destFile.toString())));
			System.out.println("---------------arrived here5b");

			// Initilisation d'un flux de sortie
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			System.out.println("---------------arrived here5c");

			// Initialisation d'un Byte
			int b;

			// Parcours du fichier et ecriture dans le flux de sortie
			while((b = bis.read()) != -1) baos.write(b);
			System.out.println("---------------arrived here6");

			// On referme
			baos.flush();
			System.out.println("---------------arrived here7");

			// Construction du tableau de byte a partir du flux de sortie
			rtfResume = baos.toByteArray();
			System.out.println("---------------arrived here8");

			baos.close();
			System.out.println("---------------arrived here9");

			// Fermeture des flux
			bis.close();
			System.out.println("---------------arrived here10");
			//baos.close();


		}
		catch(Exception e){

			e.printStackTrace();

			System.out.println("--------Error caught in catch--------");

			return null;
		}
		finally{

			exporter = null;

		}


		// Retourne le tableau de bytes
		return rtfResume;

	}



	public static byte[] getReportCSVBytes(String reportName, HashMap<Object, Object> map, Collection<?> maCollection, String nameFile) throws Exception {

		byte[] rtfResume = null;
		File sourceFile = null;

		//JRXlsxExporter exporter = null;

		JRCsvExporter exporter = null;

		try{

			sourceFile = new File(reportName);

			//exporter = new JRXlsxExporter();
			exporter = new JRCsvExporter();
			//final ByteArrayOutputStream rtfStream = new ByteArrayOutputStream();
			JasperPrint jp = printReport(reportName, map, maCollection);
			System.out.println("-----------arrived here1");

			exporter.setParameter(JRCsvExporterParameter.JASPER_PRINT, jp);
			System.out.println("---------------arrived here2");
			
			exporter.setParameter(JRCsvExporterParameter.FIELD_DELIMITER, "|");
		    exporter.setParameter(JRCsvExporterParameter.RECORD_DELIMITER,System.getProperty("line.separator"));
		    ////exporter.setParameter(JRCsvExporterParameter.OUTPUT_STREAM, ouputStream);
			
			File destFile = new File(sourceFile.getParent(), nameFile);
			System.out.println("----source file---" + sourceFile.getParent());
			System.out.println("---------------arrived here3");

			exporter.setParameter(JRCsvExporterParameter.OUTPUT_FILE_NAME, destFile.toString());
			System.out.println("---------------arrived here4");

			exporter.exportReport();
			System.out.println("---------------arrived here5a");

			//if(!new File("C://statistiques_voeux.xlsx").exists()) return rtfResume;

			// Obtention d'un InputStream du le fichier html genere
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(destFile.toString())));
			System.out.println("---------------arrived here5b");

			// Initilisation d'un flux de sortie
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			System.out.println("---------------arrived here5c");

			// Initialisation d'un Byte
			int b;

			// Parcours du fichier et ecriture dans le flux de sortie
			while((b = bis.read()) != -1) baos.write(b);
			System.out.println("---------------arrived here6");

			// On referme
			baos.flush();
			System.out.println("---------------arrived here7");

			// Construction du tableau de byte a partir du flux de sortie
			rtfResume = baos.toByteArray();
			System.out.println("---------------arrived here8");

			baos.close();
			System.out.println("---------------arrived here9");

			// Fermeture des flux
			bis.close();
			System.out.println("---------------arrived here10");
			//baos.close();



		}
		catch(Exception e){

			e.printStackTrace();

			System.out.println("--------Error caught in catch--------");

			return null;
		}
		finally{

			exporter = null;

		}


		// Retourne le tableau de bytes
		return rtfResume;



	}


	public static byte[] exportReportToWordFile(String reportName, HashMap<Object, Object> map, Collection<?> maCollection) throws Exception {

		// Construction du JasperPrint
		/*JasperPrint jp = printReport(reportName, map, maCollection);

	final JRRtfExporter rtfExporter = new JRRtfExporter();
    final ByteArrayOutputStream rtfStream = new ByteArrayOutputStream();
    rtfExporter.setParameter(JRExporterParameter.JASPER_PRINT,jp);
    rtfExporter.setParameter(JRExporterParameter.OUTPUT_STREAM,rtfStream);
    rtfExporter.exportReport();*/

		// Export de l'etat vers le fichier html specifie
		//JasperExportManager.exportReportToHtmlFile(jp, destFileName);

		//System.out.println("arrived here");

		// ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
		//OutputStream outputfile = new FileOutputStream(new File("C://statistiques_credits_scolaires.doc"));
		byte[] data = null; 

		AWDocExporter exporter = new AWDocExporter();

		File sourceFile = new File(reportName);
		//System.out.println("arrived here too");
		//JasperReport jasperReport = (JasperReport)JRLoader.loadObject(sourceFile);
		//JasperPrint jasperPrint = (JasperPrint)JRLoader.loadObject(sourceFile);
		JasperPrint jasperPrint = printReport(reportName, map, maCollection);
		// System.out.println("arrived here also");

		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);

		File destFile = new File(sourceFile.getParent(), "statistiques_credits_scolaires.doc");


		exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile.toString());

		exporter.exportReport();

		//if(!new File(destFile.toString()).exists()) return data;

		//destFile = "C://statistiques_credits_scolaires.doc";
		// Obtention d'un InputStream du le fichier html genere
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(destFile.toString())));

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		//exporter.setParameter(JRExporterParameter.INPUT_STREAM, jasperPrint);

		int b;

		// Parcours du fichier et ecriture dans le flux de sortie
		while((b = bis.read()) != -1)
			baos.write(b);

		//exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);

		//System.out.println("and here");

		//System.out.println(sourceFile.getParent());
		//System.out.println(jasperPrint.getName());

		//System.out.println("and here too");

		//exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, "C://statistiques_credits_scolaires.doc");
		//System.out.println("and here also");
		//outputfile.write(outputByteArray.toByteArray());



		// On referme
		baos.flush();

		// Construction du tableau de byte a partir du flux de sortie
		data = baos.toByteArray();
		//System.out.println("-----------------here aa too");
		// Fermeture des flux
		bis.close();
		//baos.close();
		//System.out.println("-----------------here aaa too");
		// Retourne le tableau de bytes

		//System.out.println(data);


		return data;
		//return outputByteArray.toByteArray();
	}


	public static byte[] exportReportToWordFile2(String reportName, HashMap<Object, Object> map, Collection<?> maCollection) throws Exception {

		// Construction du JasperPrint
		/*JasperPrint jp = printReport(reportName, map, maCollection);

		final JRRtfExporter rtfExporter = new JRRtfExporter();
	    final ByteArrayOutputStream rtfStream = new ByteArrayOutputStream();
	    rtfExporter.setParameter(JRExporterParameter.JASPER_PRINT,jp);
	    rtfExporter.setParameter(JRExporterParameter.OUTPUT_STREAM,rtfStream);
	    rtfExporter.exportReport();*/

		// Export de l'etat vers le fichier html specifie
		//JasperExportManager.exportReportToHtmlFile(jp, destFileName);

		//System.out.println("arrived here");

		// ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
		//OutputStream outputfile = new FileOutputStream(new File("C://statistiques_credits_scolaires.doc"));
		byte[] data = null; 

		AWDocExporter exporter = new AWDocExporter();

		File sourceFile = new File(reportName);
		//System.out.println("arrived here too");
		//JasperReport jasperReport = (JasperReport)JRLoader.loadObject(sourceFile);
		//JasperPrint jasperPrint = (JasperPrint)JRLoader.loadObject(sourceFile);
		JasperPrint jasperPrint = printReport(reportName, map, maCollection);
		// System.out.println("arrived here also");

		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);

		File destFile = new File(sourceFile.getParent(), "statistiques_credits_fin_annee.doc");

		exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile.toString());

		exporter.exportReport();

		//if(!new File(destFile.toString()).exists()) return data;

		//destFile = "C://statistiques_credits_scolaires.doc";
		// Obtention d'un InputStream du le fichier html genere
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(destFile.toString())));

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		//exporter.setParameter(JRExporterParameter.INPUT_STREAM, jasperPrint);

		int b;

		// Parcours du fichier et ecriture dans le flux de sortie
		while((b = bis.read()) != -1)
			baos.write(b);

		//exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);

		//System.out.println("and here");

		//System.out.println(sourceFile.getParent());
		//System.out.println(jasperPrint.getName());

		//System.out.println("and here too");

		//exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, "C://statistiques_credits_scolaires.doc");
		//System.out.println("and here also");
		//outputfile.write(outputByteArray.toByteArray());



		// On referme
		baos.flush();

		// Construction du tableau de byte a partir du flux de sortie
		data = baos.toByteArray();
		//System.out.println("-----------------here aa too");
		// Fermeture des flux
		bis.close();
		//baos.close();
		//System.out.println("-----------------here aaa too");
		// Retourne le tableau de bytes

		//System.out.println(data);


		return data;
		//return outputByteArray.toByteArray();
	}

	public static byte[] exportReportToWordFile3(String reportName, HashMap<Object, Object> map, Collection<?> maCollection) throws Exception {

		// Construction du JasperPrint
		/*JasperPrint jp = printReport(reportName, map, maCollection);

		final JRRtfExporter rtfExporter = new JRRtfExporter();
	    final ByteArrayOutputStream rtfStream = new ByteArrayOutputStream();
	    rtfExporter.setParameter(JRExporterParameter.JASPER_PRINT,jp);
	    rtfExporter.setParameter(JRExporterParameter.OUTPUT_STREAM,rtfStream);
	    rtfExporter.exportReport();*/

		// Export de l'etat vers le fichier html specifie
		//JasperExportManager.exportReportToHtmlFile(jp, destFileName);

		//System.out.println("arrived here");

		// ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
		//OutputStream outputfile = new FileOutputStream(new File("C://statistiques_credits_scolaires.doc"));
		byte[] data = null; 

		AWDocExporter exporter = new AWDocExporter();

		File sourceFile = new File(reportName);
		//System.out.println("arrived here too");
		//JasperReport jasperReport = (JasperReport)JRLoader.loadObject(sourceFile);
		//JasperPrint jasperPrint = (JasperPrint)JRLoader.loadObject(sourceFile);
		JasperPrint jasperPrint = printReport(reportName, map, maCollection);
		// System.out.println("arrived here also");

		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);

		File destFile = new File(sourceFile.getParent(), "statistiques_credits_classiques.doc");

		exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile.toString());

		exporter.exportReport();

		//if(!new File(destFile.toString()).exists()) return data;

		//destFile = "C://statistiques_credits_scolaires.doc";
		// Obtention d'un InputStream du le fichier html genere
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(destFile.toString())));

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		//exporter.setParameter(JRExporterParameter.INPUT_STREAM, jasperPrint);

		int b;

		// Parcours du fichier et ecriture dans le flux de sortie
		while((b = bis.read()) != -1)
			baos.write(b);

		//exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);

		//System.out.println("and here");

		//System.out.println(sourceFile.getParent());
		//System.out.println(jasperPrint.getName());

		//System.out.println("and here too");

		//exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, "C://statistiques_credits_scolaires.doc");
		//System.out.println("and here also");
		//outputfile.write(outputByteArray.toByteArray());



		// On referme
		baos.flush();

		// Construction du tableau de byte a partir du flux de sortie
		data = baos.toByteArray();
		//System.out.println("-----------------here aa too");
		// Fermeture des flux
		bis.close();
		//baos.close();
		//System.out.println("-----------------here aaa too");
		// Retourne le tableau de bytes

		//System.out.println(data);


		return data;
		//return outputByteArray.toByteArray();
	}

	public static byte[] exportReportToWordFile4(String reportName, HashMap<Object, Object> map, Collection<?> maCollection) throws Exception {

		// Construction du JasperPrint
		/*JasperPrint jp = printReport(reportName, map, maCollection);

		final JRRtfExporter rtfExporter = new JRRtfExporter();
	    final ByteArrayOutputStream rtfStream = new ByteArrayOutputStream();
	    rtfExporter.setParameter(JRExporterParameter.JASPER_PRINT,jp);
	    rtfExporter.setParameter(JRExporterParameter.OUTPUT_STREAM,rtfStream);
	    rtfExporter.exportReport();*/

		// Export de l'etat vers le fichier html specifie
		//JasperExportManager.exportReportToHtmlFile(jp, destFileName);

		//System.out.println("arrived here");

		// ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
		//OutputStream outputfile = new FileOutputStream(new File("C://statistiques_credits_scolaires.doc"));
		byte[] data = null; 

		AWDocExporter exporter = new AWDocExporter();

		File sourceFile = new File(reportName);
		//System.out.println("arrived here too");
		//JasperReport jasperReport = (JasperReport)JRLoader.loadObject(sourceFile);
		//JasperPrint jasperPrint = (JasperPrint)JRLoader.loadObject(sourceFile);
		JasperPrint jasperPrint = printReport(reportName, map, maCollection);
		// System.out.println("arrived here also");

		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);

		File destFile = new File(sourceFile.getParent(), "statistiques_credits_religieux.doc");

		exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile.toString());

		exporter.exportReport();

		//if(!new File(destFile.toString()).exists()) return data;

		//destFile = "C://statistiques_credits_scolaires.doc";
		// Obtention d'un InputStream du le fichier html genere
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(destFile.toString())));

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		//exporter.setParameter(JRExporterParameter.INPUT_STREAM, jasperPrint);

		int b;

		// Parcours du fichier et ecriture dans le flux de sortie
		while((b = bis.read()) != -1)
			baos.write(b);

		//exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);

		//System.out.println("and here");

		//System.out.println(sourceFile.getParent());
		//System.out.println(jasperPrint.getName());

		//System.out.println("and here too");

		//exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, "C://statistiques_credits_scolaires.doc");
		//System.out.println("and here also");
		//outputfile.write(outputByteArray.toByteArray());



		// On referme
		baos.flush();

		// Construction du tableau de byte a partir du flux de sortie
		data = baos.toByteArray();
		//System.out.println("-----------------here aa too");
		// Fermeture des flux
		bis.close();
		//baos.close();
		//System.out.println("-----------------here aaa too");
		// Retourne le tableau de bytes

		//System.out.println(data);


		return data;
		//return outputByteArray.toByteArray();
	}

	public static byte[] getReportWordBytes(String reportName, HashMap<Object, Object> map, Collection<?> maCollection) throws Exception {

		byte[] data = null;

		//System.out.println("-----------------here");
		//final Applicant applicant = this.getApplicantBySsn(ssnNumber);
		exportReportToWordFile(reportName, map, maCollection);
		//System.out.println("-----------------here too");
		//rtfResume = rtfStream.toByteArray();

		if(!new File("C://statistiques_credits_scolaires.doc").exists()) return data;

		// Obtention d'un InputStream du le fichier html genere
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File("C://statistiques_credits_scolaires.doc")));
		//OutputStream outputfile = new FileOutputStream(new File("C://statistiques_credits_scolaires.doc"));
		//System.out.println("-----------------here a too");
		// Initilisation d'un flux de sortie
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		//outputfile.write(baos.toByteArray());
		// Initialisation d'un Byte
		int b;

		// Parcours du fichier et ecriture dans le flux de sortie
		while((b = bis.read()) != -1) baos.write(b);

		// On referme
		baos.flush();

		// Construction du tableau de byte a partir du flux de sortie
		data = baos.toByteArray();
		//System.out.println("-----------------here aa too");
		// Fermeture des flux
		bis.close();
		//baos.close();
		//System.out.println("-----------------here aaa too");
		// Retourne le tableau de bytes
		return data;



	}

	public void complete(){
		try {

			// Information
			information = true;

			// Message d'information

			ExceptionHelper.showInformation("Opération Effectuée avec Succès. Cliquez sur le lien de téléchargement pour télécharger vos statistiques en fichier excel nommé statistiques_taux_de_conformite.xlsx dans le repertoire C://Utilisateurs/Téléchargement de votre machine.", this);


		} catch (Exception e) {

			// Mise en place de l'etat d'inclusion du fichier d'erreur
			this.error = true;

			// Traitement de l'exception
			ExceptionHelper.threatException(e);
		}
	}


}
