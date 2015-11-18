package bg.proxiad.demo.championship.customtags;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.SkipPageException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class ShowAttributeRowTag extends SimpleTagSupport {
    
    private String label;
    private String content;

    public void setLabel(String label) {
        this.label = label;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    @Override
    public void doTag() throws JspException, IOException {
        try {
            getJspContext().getOut().write("<div class=\"row\">\n" +
            "            <label class=\"col-sm-2\">"+label+"</label>\n" +
            "            <div class=\"col-sm-10\">"+ content +"</div>\n" +
            "        </div>");
        } catch (Exception e) {
            e.printStackTrace();
            // stop page from loading further by throwing SkipPageException
            throw new SkipPageException("Exception in ShowAttributeRowTag: " + e.getMessage());
        }
    }
}
