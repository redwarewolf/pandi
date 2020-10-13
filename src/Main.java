import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


class Main {
	
  static GraphicsConfiguration gc;
  static JTextField messageTextfield, keyTextfield, IVtextField;
  static Map<String, String> dictionary = new HashMap<String, String>();

	
  public static void main(String[] args) {  
    
    
    
    JFrame frame= new JFrame(gc);
    frame.getContentPane().setLayout(new GridLayout(0,1));
    JPanel panel = new JPanel();
    panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
    panel.setLayout(new GridLayout(0,1));
    frame.add(panel,BorderLayout.CENTER);
	frame.setVisible(true);
	frame.setTitle("Pandi");
	
	JButton fileOpener=new JButton("Open File");
	fileOpener.setBounds(100,100,140, 40); 
	
	JLabel messageLabel = new JLabel();		
	messageLabel.setText("CSV Selected:");
	messageLabel.setBounds(10, 10, 100, 100);	
	
	JLabel fileContentLabel = new JLabel();		
	fileContentLabel.setText("-----");
	fileContentLabel.setBounds(10, 10, 100, 100);
	
	panel.add(fileOpener);
       
    
	panel.add(messageLabel);
	panel.add(fileContentLabel);
    
	JButton fileRenamer=new JButton("Change File Names");
	fileRenamer.setBounds(100,100,140, 40);
	
	panel.add(fileRenamer);
	
    
	fileOpener.addActionListener(new ActionListener() {
    	@Override
    	public void actionPerformed(ActionEvent arg0) {
    		JFileChooser chooser= new JFileChooser();
            chooser.setCurrentDirectory(new File("c:\\"));
            chooser.showOpenDialog(null);
            File file = chooser.getSelectedFile();
            String path= file.getAbsolutePath();
            
            try{
            	// Lógica tras abrir el archivo a encriptar
            	
            	System.out.println(path);
                
                fileContentLabel.setText(file.getName());
                dictionary = Pandi.readFile(file);
                
            }catch(Exception e){
                JOptionPane.showMessageDialog(null,e);
            }
    	}
    });
    
    
	fileRenamer.addActionListener(new ActionListener() {
    	@Override
    	public void actionPerformed(ActionEvent arg0) {
    		JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int option = fileChooser.showOpenDialog(frame);
            if(option == JFileChooser.APPROVE_OPTION){
               File folder = fileChooser.getSelectedFile();
               System.out.println("Folder Selected: " + folder.getPath());
               
               try (Stream<Path> walk = Files.walk(Paths.get(folder.getPath()))) {

            		List<String> result = walk.filter(Files::isRegularFile)
            				.map(x -> x.toString()).collect(Collectors.toList());

            		result.forEach((final String path)-> {
            			System.out.println(path);
            			String[] fileNameAux = path.split(Pattern.quote(File.separator));
            			System.out.println(fileNameAux[fileNameAux.length - 1]);
            			String fileName = fileNameAux[fileNameAux.length - 1];
            			String cuil = fileName.split("-")[0];            			
            			Path source = Paths.get(path);
            			Path target = Paths.get(path.replace(cuil, dictionary.get(cuil)+ '-' + cuil));
            		    try { 
							Files.move(source,target);
						} catch (IOException e) {
							e.printStackTrace();
						}


            		});
        		    JOptionPane.showMessageDialog(frame, "Archivos Renombrados!");


            	} catch (IOException e) {
            		e.printStackTrace();
            	}
            }
    	}
    });
    
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    frame.pack();

  }
}


