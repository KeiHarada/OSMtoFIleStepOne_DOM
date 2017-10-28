package osm;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.sun.jmx.snmp.Timestamp;

import graph.SMEdge;
import graph.SMNode;

public class Main {

	public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
		System.out.println(new Timestamp(System.currentTimeMillis()).toString());
		// Import File
		File f;

		f = new File(args[0]+".osm");

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(f);
		doc.getDocumentElement().normalize();

		HashMap<Long, SMNode> nodes = new HashMap<Long, SMNode>();
		HashMap<Integer, Long> index = new HashMap<Integer, Long>();
		ArrayList<SMEdge> edges = new ArrayList<SMEdge>();
		System.out.println("Doing Nodes");
		NodeList nl = doc.getElementsByTagName("node");
		for (int i = 0; i < nl.getLength(); i++) {
			Element e = (Element) nl.item(i);
			nodes.put(Long.parseLong(e.getAttribute("id")), new SMNode(i,Double.parseDouble(e.getAttribute("lat")),Double.parseDouble(e.getAttribute("lon"))));
			index.put(i,Long.parseLong(e.getAttribute("id")));
		}
		System.out.println("Doing Edges");
		nl = doc.getElementsByTagName("way");
		for (int i = 0; i < nl.getLength(); i++) {
			Element way = (Element) nl.item(i);
			NodeList wayList = way.getElementsByTagName("nd");
			for (int j = 0; j < wayList.getLength(); j++) {
				if (j > 0) {
					SMNode from = nodes.get(Long.parseLong(((Element) wayList.item(j - 1)).getAttribute("ref")));
					SMNode to = nodes.get(Long.parseLong(((Element) wayList.item(j)).getAttribute("ref")));
					if (from != null && to != null) {
						edges.add(new SMEdge(from, to));
					} else {
						if (from == null) {
							System.out.println(((Element) wayList.item(j - 1)).getAttribute("ref"));
						}
						if (to == null) {
							System.out.println(((Element) wayList.item(j)).getAttribute("ref"));
						}
					}
				}
			}
		}
		
		/* Exporting to unrefined files. */
		BufferedWriter nodeBW = new BufferedWriter(new FileWriter(new File(args[0]+"_nodes.txt")));
		BufferedWriter edgeBW = new BufferedWriter(new FileWriter(new File(args[0]+"_edges.txt")));
		//nodeBW.write(nodes.size() + System.getProperty("line.separator")); // you must comment out this statement, if it needs not to write the number of nodes at the top of output file.
		for (int i = 0; i < nodes.size(); i++) {
			SMNode n = nodes.get(index.get(i));
			nodeBW.write(n.getId() + "\t" + n.getLatitude() + "\t" + n.getLongitude() + System.getProperty("line.separator"));
		}
		for (SMEdge e : edges) {
			edgeBW.write(e.getFrom().getId() + "\t" + e.getTo().getId() + "\t" + e.getDist() + System.getProperty("line.separator"));
		}
		nodeBW.close();
		edgeBW.close();
		System.out.println(new Timestamp(System.currentTimeMillis()).toString());
	}
}
