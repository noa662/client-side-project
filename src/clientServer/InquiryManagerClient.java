package clientServer;

import data.Complaint;
import data.Inquiry;
import data.Question;
import data.Request;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class InquiryManagerClient {

    private Socket connectToServer;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private static final int SERVER_PORT = 3030;
    private static final String SERVER_HOST = "localhost";
    private Scanner scanner = new Scanner(System.in);

    public InquiryManagerClient() {
        try {
            connectToServer = new Socket(SERVER_HOST, SERVER_PORT);
            System.out.println("connected to server successfully!");
            Execute();
        } catch (IOException e) {
            System.out.println("problem connecting to the server ");
            e.printStackTrace();
        }
    }

    public void Execute() {
        int choice = 0;
        System.out.println("select an action:");
        System.out.println("show all inquiries -> 1");
        System.out.println("add new inquiry -> 2");
        System.out.println("cancel inquiry -> 3");
        System.out.println("get status-> 4");
        System.out.println("get representative -> 5");
        System.out.println("get representative inquiries -> 6");
        System.out.println("get map -> 7");
        System.out.println("get month statistics -> 8");
        System.out.println("exit -> 9");
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("invalid input! please try again");
        }
        RequestData requestData = new RequestData();
        int code;
        switch (choice) {
            case 1:
                requestData.setAction(InquiryManagerActions.ALL_INQUIRY);
                break;
            case 2:
                Inquiry inquiry = addNewInquiry();
                requestData.setAction(InquiryManagerActions.ADD_INQUIRY);
                requestData.setParameters(inquiry);
                break;
            case 3:
                System.out.println("insert the inquiry code");
                code=scanner.nextInt();
                requestData.setAction(InquiryManagerActions.CANCLE_INQUIRY);
                requestData.setParameters(code);
                break;
            case 4:
                System.out.println("insert the inquiry code");
                code=scanner.nextInt();
                requestData.setAction(InquiryManagerActions.GET_STATUS);
                requestData.setParameters(code);
                break;
            case 5:
                System.out.println("insert the inquiry code");
                code=scanner.nextInt();
                requestData.setAction(InquiryManagerActions.GET_REPRESENTATIVE);
                requestData.setParameters(code);
                break;
            case 6:
                System.out.println("insert the representative code");
                code=scanner.nextInt();
                requestData.setAction(InquiryManagerActions.GET_REPRESENTATIVE_INQUIRIES);
                requestData.setParameters(code);
                break;
            case 7:
                requestData.setAction(InquiryManagerActions.GET_MAP);
                break;
            case 8:
                System.out.println("insert month");
                code=scanner.nextInt();
                requestData.setAction(InquiryManagerActions.GET_MONTHYFILESTATS);
                requestData.setParameters(code);
                break;
            case 9:
                System.out.println("exit..");
                closeConnection();
                return;
            default:
                System.out.println("invalid input! please try again");
        }
        sendRequest(requestData);
        ResponseData responseData = receiveResponse();
        printResponse(responseData);
        closeConnection();
    }

    public void sendRequest(RequestData requestData) {
        try {
            out = new ObjectOutputStream(connectToServer.getOutputStream());
            out.flush();
            out.writeObject(requestData);
        } catch (IOException e) {
            System.out.println("error sending request to server " + e.getMessage());
        }
    }

    public ResponseData receiveResponse() {
        ResponseData responseData=new ResponseData();
        try {
            in = new ObjectInputStream(connectToServer.getInputStream());
            responseData = (ResponseData) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("error receiving server response " + e.getMessage());
        }
        return responseData;
    }

    public void printResponse(ResponseData responseData) {
        System.out.println("status: " + responseData.getStatus());
        System.out.println("message: " + responseData.getMessage());
        System.out.println("result: " + responseData.getResult());
    }

    public Inquiry addNewInquiry() {
        int type = 0;
        while (type < 1 || type > 3) {
            System.out.println("select inquiry type:");
            System.out.println("1 -> Complaint");
            System.out.println("2 -> Request");
            System.out.println("3 -> Question");
            try {
                type = Integer.parseInt(scanner.nextLine());
                if (type < 1 || type > 3) {
                    System.out.println("invalid choice, please enter 1, 2, or 3.");
                }
            } catch (NumberFormatException e) {
                System.out.println("invalid input. please enter a number.");
            }
        }
        Inquiry inquiry=null;
        switch (type) {
            case 1:
                inquiry = new Complaint();
                break;
            case 2:
                inquiry = new Request();
                break;
            case 3:
                inquiry = new Question();
                break;
            default:
                System.out.println("invalid choise, please try again");
                break;
        }
        inquiry.fillDataByUser();
        return inquiry;
    }



    public void closeConnection() {
        try {
            in.close();
            out.close();
            System.out.println("connection to the server was closed.");
        } catch (IOException e) {
            System.out.println("error close the connection " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        InquiryManagerClient client=new InquiryManagerClient();
    }

}
