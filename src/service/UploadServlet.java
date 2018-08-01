package service;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
 
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import entity.Items;
import uploadImages_jdbc.uploadImages_test;
 

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
     
    // �ϴ��ļ��洢Ŀ¼
    private static final String UPLOAD_DIRECTORY = "upload";
 
    // �ϴ�����
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
    private static String ImagePath;
    /**
     * �ϴ����ݼ������ļ�
     */
    
    protected void doPost(HttpServletRequest request,
        HttpServletResponse response) throws ServletException, IOException {
        // ����Ƿ�Ϊ��ý���ϴ�
        if (!ServletFileUpload.isMultipartContent(request)) {
            // ���������ֹͣ
            PrintWriter writer = response.getWriter();
            writer.println("Error: ��������� enctype=multipart/form-data");
            writer.flush();
            return;
        }

        DataOutputStream dos = null;
        FileInputStream fis=null;
        Socket socket=null;
        
        // �����ϴ�����
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // �����ڴ��ٽ�ֵ - �����󽫲�����ʱ�ļ����洢����ʱĿ¼��
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        // ������ʱ�洢Ŀ¼
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
 
        ServletFileUpload upload = new ServletFileUpload(factory);
         
        // ��������ļ��ϴ�ֵ
        upload.setFileSizeMax(MAX_FILE_SIZE);
         
        // �����������ֵ (�����ļ��ͱ�����)
        upload.setSizeMax(MAX_REQUEST_SIZE);
        
        // ���Ĵ���
        upload.setHeaderEncoding("UTF-8"); 

        // ������ʱ·�����洢�ϴ����ļ�
        // ���·����Ե�ǰӦ�õ�Ŀ¼
        String uploadPath = getServletContext().getRealPath("./") + File.separator + UPLOAD_DIRECTORY;
         
        // ���Ŀ¼�������򴴽�
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
 
        try {
            // ���������������ȡ�ļ�����
            @SuppressWarnings("unchecked")
            List<FileItem> formItems = upload.parseRequest(request);
 
            if (formItems != null && formItems.size() > 0) {
                // ����������
                for (FileItem item : formItems) {
                    // �����ڱ��е��ֶ�
                    if (!item.isFormField()) {
                        String fileName = new File(item.getName()).getName();
                        String filePath = uploadPath + File.separator + fileName;
                       //System.out.println(uploadPath+File.separator+fileName);
                        UploadServlet.ImagePath=UPLOAD_DIRECTORY +"\\"+fileName;
                        item.setFieldName(fileName);
                        File storeFile = new File(filePath);

                        //�����ͻ���Socket��ָ����������ַ�Ͷ˿�
            			socket=new Socket("localhost",8888);
            			OutputStream os=socket.getOutputStream();//�ֽ���� ��
//            			dos=new DataOutputStream(socket.getOutputStream());
//            			byte[] sendBytes=new byte[1024];
//            			while((length=fis.read(sendBytes,0,sendBytes.length))>0)
//            			{
//            				dos.write(sendBytes,0,length);
//            				dos.flush();
//            				
//            			}
            			PrintWriter pw=new PrintWriter(os);//���������װΪ��ӡ��
            			//pw.write("�û�����admin2;���룺456"); //����
            			//pw.write(filePath);
            			pw.write(UploadServlet.ImagePath);
            			pw.flush();

            			socket.shutdownOutput(); //�ر������
                        
                        // �ڿ���̨����ļ����ϴ�·��
                        System.out.println(filePath);
                     // �����ļ���Ӳ��
                        item.write(storeFile);
                        String FP=storeFile.getPath();
                        fis=new FileInputStream(FP);
                        
                        request.setAttribute("message",
                            "�ļ��ϴ��ɹ�!");
                    }
                }
            }
        } catch (Exception ex) {
            request.setAttribute("message",
                    "������Ϣ: " + ex.getMessage());
        }
        finally{
        	if(dos!=null){dos.close();}
        	if(fis!=null){fis.close();}
        	if(socket!=null){socket.close();}
        }
        // ��ת�� message.jsp
        getServletContext().getRequestDispatcher("/message.jsp").forward(request, response);
    }
    
    public String getImagesPath()
    {
    	String str=ImagePath;
    	return str;
    }
    
    
    
}