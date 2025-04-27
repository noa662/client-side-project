package clientServer;

import data.Complaint;
import data.Inquiry;
import data.Question;
import data.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
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
            out = new ObjectOutputStream(connectToServer.getOutputStream());
            in = new ObjectInputStream(connectToServer.getInputStream());
            System.out.println("connected to server successfully!");
        } catch (IOException e) {
            System.out.println("problem connecting to the server " + e.getMessage());
        }
    }

    public void Execute() {
        int choice = 0;
        while (choice != 3) {
            System.out.println("select an action:");
            System.out.println("show all inquiries -> 1");
            System.out.println("add new inquiry -> 2");
            System.out.println("exit -> 3");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("invalid input! please try again");
                continue;
            }
            RequestData requestData = new RequestData();
            switch (choice) {
                case 1:
                    requestData.setAction(InquiryManagerActions.ALL_INQUIRY);
                    //קבלת כל הפניות??
                    requestData.setParameters(new ArrayList<>());
                    break;
                case 2:
                    Inquiry inquiry = addNewInquiry();
                    requestData.setAction(InquiryManagerActions.ADD_INQUIRY);
                    requestData.setParameters(List.of(inquiry));
                    break;
                case 3:
                    System.out.println("exit..");
                    closeConnection();
                    return;
                default:
                    System.out.println("invalid input! please try again");
                    continue;
            }
            sendRequest(requestData);
            ResponseData responseData = receiveResponse();
            printResponse(responseData);
        }
    }

    public void sendRequest(RequestData requestData) {
        try {
            out.writeObject(requestData);
            out.flush();
        } catch (IOException e) {
            System.out.println("error sending request to server " + e.getMessage());
        }
    }

    public ResponseData receiveResponse() {
        ResponseData responseData;
        try {
            responseData = (ResponseData) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("error receiving server response " + e.getMessage());
            responseData = new ResponseData(ResponseStatus.FAIL, "error receiving server response", null);
            return responseData;
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
        Inquiry inquiry;
        switch (type) {
            case 2:
                inquiry = new Request();
                break;
            case 3:
                inquiry = new Question();
                break;
            default:
                inquiry = new Complaint();
                break;
        }
        inquiry.fillDataByUser();
        return inquiry;
    }

    public void closeConnection() {
        try {
            in.close();
            out.close();
            connectToServer.close();
            System.out.println("connection to the server was closed.");
        } catch (IOException e) {
            System.out.println("error close the connection " + e.getMessage());
        }
    }

}
