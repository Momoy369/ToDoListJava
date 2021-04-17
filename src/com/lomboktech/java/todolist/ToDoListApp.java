package com.lomboktech.java.todolist;

import com.sun.rmi.rmid.ExecPermission;
import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ToDoListApp {

    //deklarasi variabel
    static String fileName;
    static ArrayList<String> toDoLists;
    static boolean isEditing = false;
    static Scanner input;

    public static void main(String[] args){
        //Inisialisasi
        toDoLists = new ArrayList<>();
        input = new Scanner(System.in);

        String filePath = System.console() == null ? "/src/todolist.txt" : "/todolist.txt";
        fileName = System.getProperty("user.dir") + filePath;

        System.out.println("FILE: " +fileName);

        //Run program
        while(true){
            showMenu();
        }
    }

    static void clearScreen(){
        try{
            final String os = System.getProperty("os.name");
            if(os.contains("Windows")){
                //clear screen untuk windows
                new ProcessBuilder("cmd", "/c", "cls")
                        .inheritIO()
                        .start()
                        .waitFor();
            } else {
                //Clear screen untuk Linux, Unix, Mac
                Runtime.getRuntime().exec("clear");
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (final Exception e){
            System.out.println("Error karena: " + e.getMessage());
        }
    }

    static void showMenu(){
        System.out.println("=== TODO LIST APP ===");
        System.out.println("[1] Lihat Todo List");
        System.out.println("[2] Tambah Todo List");
        System.out.println("[3] Edit Todo List");
        System.out.println("[4] Hapus Todo List");
        System.out.println("[0] Keluar");
        System.out.println("----------------------");
        System.out.print("Pilih Menu> ");

        String selectedMenu = input.nextLine();

        if(selectedMenu.equals("1")){
            showToDoList();
        } else if(selectedMenu.equals("2")){
            addToDoList();
        } else if(selectedMenu.equals("3")){
            editToDoList();
        } else if(selectedMenu.equals("4")){
            deleteToDoList();
        } else if(selectedMenu.equals("0")){
            System.exit(0);
        } else {
            System.out.println("Anda salah memilih menu!");
            backToMenu();
        }
    }

    static void backToMenu(){
        System.out.println("");
        System.out.print("Tekan Enter untuk kembali...");
        input.nextLine();
        clearScreen();
    }

    static void readToDoList(){
        try{
            File file = new File(fileName);
            Scanner fileReader = new Scanner(file);

            //load isi file ke dalam array todoList
            toDoLists.clear();
            while (fileReader.hasNextLine()){
                String data = fileReader.nextLine();
                toDoLists.add(data);
            }
        } catch(FileNotFoundException e){
            System.out.println("Error karena: " +e.getMessage());
        }
    }

    static void showToDoList(){
        clearScreen();
        readToDoList();
        if(toDoLists.size() > 0){
            System.out.println("TODO LIST: ");
            int index = 0;
            for(String data : toDoLists){
                System.out.println(String.format("[%d] %s", index, data));
                index++;
            }
        } else {
            System.out.println("Tidak ada data!");
        }

        if(!isEditing){
            backToMenu();
        }
    }

    static void addToDoList(){
        clearScreen();

        System.out.println("Apa yang ingin Anda lakukan?");
        System.out.print("Jawab: ");
        String newTodoList = input.nextLine();

        try{
            //Menulis file
            FileWriter fileWriter = new FileWriter(fileName, true);
            fileWriter.append(String.format("%s%n", newTodoList));
            fileWriter.close();
            System.out.println("Berhasil ditambahkan!");
        } catch(IOException e){
            System.out.println("Terjadi kesalahan karena: " +e.getMessage());
        }
        backToMenu();
    }

    static void editToDoList(){
        isEditing = true;
        showToDoList();

        try{
            System.out.println("----------------");
            System.out.print("Pilih Indeks> ");
            int index = Integer.parseInt(input.nextLine());

            if (index > toDoLists.size()){
                throw new IndexOutOfBoundsException("Kamu memasukkan data yang salah!");
            } else {
                System.out.println("Data baru: ");
                String newData = input.nextLine();

                //Update data
                toDoLists.set(index, newData);

                System.out.println(toDoLists.toString());

                try{
                    FileWriter fileWriter = new FileWriter(fileName, false);

                    //Write new data
                    for(String data : toDoLists){
                        fileWriter.append(String.format("%s%n", data));
                    }
                    fileWriter.close();
                    System.out.println("Berhasil diubah!");
                } catch (IOException e){
                    System.out.println("Terjadi kesalahan karena: " +e.getMessage());
                }
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        isEditing = false;
        backToMenu();
    }

    static void deleteToDoList(){
        isEditing = true;
        showToDoList();

        System.out.println("-------------------");
        System.out.print("Pilih Indeks> ");
        int index = Integer.parseInt(input.nextLine());

        try{
            if (index > toDoLists.size()){
                throw new IndexOutOfBoundsException("Anda memasukkan data yang salah!");
            } else {

                System.out.println("Anda akan menghapus: ");
                System.out.println(String.format("[%d] %s", index, toDoLists.get(index)));
                System.out.println("Apa Anda yakin?");
                System.out.print("Jawab (y/t): ");
                String jawab = input.nextLine();

                if(jawab.equalsIgnoreCase("y")){
                    toDoLists.remove(index);

                    //Tulis ulang
                    try{
                        FileWriter fileWriter = new FileWriter(fileName, false);

                        //Write new data
                        for(String data : toDoLists){
                            fileWriter.append(String.format("%s%n", data));
                        }
                        fileWriter.close();

                        System.out.println("Berhasil dihapus!");
                    } catch (IOException e){
                        System.out.println("Terjadi kesalahan karena: " +e.getMessage());
                    }
                }
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        isEditing = false;
        backToMenu();
    }
}
