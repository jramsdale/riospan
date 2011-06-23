package org.rioproject.substrates.riospan.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JPanel;

/**
 * Introduction panel for the hospital example.
 */
public class RioSpanIntro extends JPanel {

    private static final long serialVersionUID = 1L;

	public RioSpanIntro(final Object obj) {
        super(new BorderLayout(8, 8));
        getAccessibleContext().setAccessibleName("RioSpan Client");
        JEditorPane text = new JEditorPane("text/html", getIntro());
        text.setEditable(false);
        JPanel introPanel = new JPanel(new BorderLayout());
        introPanel.add(text, BorderLayout.CENTER);
        JButton hb = new JButton("<html><body><big>Go to RioSpan User Interface</big></body></html>");
        add(introPanel, BorderLayout.CENTER);
        add(hb, BorderLayout.SOUTH);
        hb.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent actionEvent) {
                RioSpanUI ui = new RioSpanUI(obj);
                ui.setVisible(true);
            }
        });
    }

    String getIntro() {
        StringBuilder sb = new StringBuilder();
        sb.append("<html><body>\n");
        sb.append("<h2>Introduction</h2>\n");
        sb.append("</body></html>");
        return sb.toString();
    }
}
