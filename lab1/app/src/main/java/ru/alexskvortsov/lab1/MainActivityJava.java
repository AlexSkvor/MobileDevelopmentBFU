package ru.alexskvortsov.lab1;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivityJava extends LoggingLifecycleActivity {

    private static final int ACTIVITY_CHOOSE_FILE = 666;

    TextView textElement;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textElement = findViewById(R.id.helloWorld);
        textElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBrowse();
            }
        });
    }

    public void onBrowse() {
        Intent chooseFile;
        Intent intent;
        chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.addCategory(Intent.CATEGORY_OPENABLE);
        chooseFile.setType("text/plain");
        intent = Intent.createChooser(chooseFile, "Choose a file");
        startActivityForResult(intent, ACTIVITY_CHOOSE_FILE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ACTIVITY_CHOOSE_FILE) {
            if (resultCode != RESULT_OK || data == null) {
                super.onActivityResult(requestCode, resultCode, data);
                return;
            }
            Uri uri = data.getData();
            if (uri == null) {
                return;
            }
            String fileName = getFileName(uri);
            String fileContent = getFileContent(uri);
            Log.e("File content: ", fileContent);
            Log.e("File name: ", fileName);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public String getFileContent(Uri contentUri) {
        try {
            InputStream in = getContentResolver().openInputStream(contentUri);
            if (in != null) {
                BufferedReader r = new BufferedReader(new InputStreamReader(in));
                StringBuilder total = new StringBuilder();
                for (String line; (line = r.readLine()) != null; ) {
                    total.append(line).append('\n');
                }
                return total.toString();
            } else {
                Log.e("TAG", "Input stream is null");
            }
        } catch (Exception e) {
            Log.e("TAG", "Error while reading file by uri", e);
        }
        return "Could not read content!";
    }

    public String getFileName(Uri contentUri) {
        String result = null;
        if (contentUri.getScheme() != null && contentUri.getScheme().equals("content")) {
            try (Cursor cursor = getContentResolver().query(contentUri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }
        }
        if (result == null) {
            result = contentUri.getPath();
            if (result == null) {
                return null;
            }
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }
}
