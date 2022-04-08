package cn.airanthem.aboot.data;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Database {
    private final Map<String, User> db = new ConcurrentHashMap<>();
    private final String file;

    public Database(String file) {
        this.file = file;

        try {
            db.putAll(loadSavedData());
            System.out.printf("previous data loaded%n");
        } catch (IOException ignored) {
            System.out.printf("no previous data%n");
        }
    }

    public boolean add(User user) {
        if (db.containsKey(user.username)) {
            return false;
        } else {
            db.put(user.username, user);
            saveData(db);
            return true;
        }
    }

    public User get(String username) {
        if (db.containsKey(username)) {
            return db.get(username);
        }
        return null;
    }

    private void saveData(Object object) {
        JSONObject jsonObject = (JSONObject) JSON.toJSON(object);

        try {
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
            osw.write(jsonObject.toJSONString());
            osw.flush();
            osw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Map<String, User> loadSavedData() throws IOException {
        String jsonString;
        File file = new File(this.file);
        FileInputStream inputStream = new FileInputStream(file);
        int size = inputStream.available();
        byte[] buffer = new byte[size];
        inputStream.read(buffer);
        inputStream.close();
        jsonString = new String(buffer, StandardCharsets.UTF_8);
        JSONObject obj = JSON.parseObject(jsonString);
        if (obj == null) {
            throw new IOException("empty data file");
        }
        return obj.values().stream().map(o -> {
            JSONObject v = (JSONObject) o;
            return v.toJavaObject(User.class);
        }).collect(Collectors.toMap(User::getUsername, u -> u));
    }
}
