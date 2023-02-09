package ezen.ftp.client;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

/**
 * 파일 다운로드 진행창
 * @author 김기정
 */
public class ProgressBarFrame extends JFrame{
	JPanel progressPanel;
	JProgressBar progressBar;
	JPanel buttonPanel;
	JButton confirmButton;
	String fileName;
	
	public ProgressBarFrame(String fileName) {
		super("File Download Progress");
		this.fileName = fileName;
		progressPanel = new JPanel();
		progressBar = new JProgressBar(0, 100);
		progressBar.setPreferredSize(new Dimension(350, 20));
		progressBar.setStringPainted(true);
		progressPanel.setBorder(new TitledBorder(fileName + "download..."));
		buttonPanel = new JPanel();
		confirmButton = new JButton("  Confirm  ");
		confirmButton.setEnabled(false);
	}

	public void setComponents(){
		progressPanel.add(progressBar);
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(confirmButton);
		add(progressPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
		setAlwaysOnTop(true);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setProgress(int value){
		progressBar.setValue(value);
		progressBar.setString(value + "% download...");
		if(value == 100){
			progressBar.setString("Download complete!");
			confirmButton.setEnabled(true);
		}
	}
	
	public void setCenter() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (dim.width - getSize().width)/2;
		int y = (dim.height - getSize().height)/2;
		setLocation(x, y);
	}
	
	private void exit(){
		setVisible(false);
		dispose();
		System.exit(0);
	}
	
	public void init() {
		setComponents();
		setSize(380, 130);
		setCenter();
		eventRegist();
		setVisible(true);
		setResizable(false);
	}
	
	/**
	 * 이벤트소스에 이벤트리스너 등록
	 */
	public void eventRegist(){
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exit();
			}
		});
		
		confirmButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				exit();
			}
		});
	}
	
	/** 테스트를 위한 메인 */
	public static void main(String[] args) throws InterruptedException {
		ProgressBarFrame frame = new ProgressBarFrame("너만봐.zip");
		frame.init();
		
		new Thread() {
			public void run() {
				for(int i=0; i<=100; i+=10) {
					frame.setProgress(i);
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
					}
				}
			};
		}.start();
		
	}

}
