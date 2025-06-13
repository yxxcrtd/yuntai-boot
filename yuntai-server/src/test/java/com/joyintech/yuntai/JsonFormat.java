package com.joyintech.yuntai;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonFormat {

    public static void main(String[] args) throws Exception {
        // 创建整个数据结构
        Map<String, Object> data = createDataStructure();

        // 使用Jackson序列化为JSON
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        String json = mapper.writeValueAsString(data);
        System.out.println(json);
    }

    // 创建整个数据结构
    public static Map<String, Object> createDataStructure() {
        Map<String, Object> data = new LinkedHashMap<>();

        // 添加第一个对象（使用 key-value map）
        Map<String, Object> obj1Fields = new LinkedHashMap<>();
        obj1Fields.put("NAME", "T3");
        obj1Fields.put("AGE", "3");
        obj1Fields.put("ADDRESS", "T-Add-3");
        obj1Fields.put("EXTRA_FIELD_1", "Extra Value 1");
        data.put("1926177721925050370", createDynamicObject("1926177721925050370", obj1Fields));

        // 添加第二个对象
        Map<String, Object> obj2Fields = new LinkedHashMap<>();
        obj2Fields.put("NAME", "TI-3");
        obj2Fields.put("AGE", "3");
        obj2Fields.put("SEX", "3");
        obj2Fields.put("OCCUPATION", "Engineer");
        data.put("1926177721941827585", createDynamicObject("1926177721941827585", obj2Fields));

        // 添加学生列表
        List<Map<String, Object>> studentList = new ArrayList<>();

        // 第一个学生组
        Map<String, Object> student1BasicFields = new LinkedHashMap<>();
        student1BasicFields.put("NAME", "S-i-3");
        student1BasicFields.put("AGE", "3");
        student1BasicFields.put("SEX", "3");
        student1BasicFields.put("NEW_FIELD", "New Value");

        Map<String, Object> student1DetailFields = new LinkedHashMap<>();
        student1DetailFields.put("STUDENT_NAME", "S3");
        student1DetailFields.put("STUDENT_NUM", "S3");
        student1DetailFields.put("STUDENT_AGE", "3");
        student1DetailFields.put("GRADE", "A");

        // 第一个学生的爱好列表
        List<Map<String, Object>> hobbies1 = new ArrayList<>();

        // 爱好1
        Map<String, Object> hobby1BasicFields = new LinkedHashMap<>();
        hobby1BasicFields.put("NAME", "S-h-i-3");
        hobby1BasicFields.put("MAIN", "S-h-i-3");
        hobby1BasicFields.put("ADDRESS", "S-h-i-3");
        hobby1BasicFields.put("COUNT", "3");
        hobby1BasicFields.put("RATING", "5");

        Map<String, Object> hobby1DetailFields = new LinkedHashMap<>();
        hobby1DetailFields.put("HOBBY_NAME", "S-h-3");
        hobby1DetailFields.put("HOBBY_DESC", "S-h-3");
        hobby1DetailFields.put("HOBBY_DESCRIPTION", "S-h-3");
        hobby1DetailFields.put("FREQUENCY", "Daily");

        hobbies1.add(createHobby(
            createDynamicObject("1926177721933438977", hobby1BasicFields),
            createDynamicObject("1926177721992159234", hobby1DetailFields)
        ));

        // 爱好2
        Map<String, Object> hobby2BasicFields = new LinkedHashMap<>();
        hobby2BasicFields.put("NAME", "S-h-i-3-2");
        hobby2BasicFields.put("MAIN", "S-h-i-3-2");
        hobby2BasicFields.put("ADDRESS", "S-h-i-3-2");
        hobby2BasicFields.put("COUNT", "3");

        Map<String, Object> hobby2DetailFields = new LinkedHashMap<>();
        hobby2DetailFields.put("HOBBY_NAME", "S-h-3-2");
        hobby2DetailFields.put("HOBBY_DESC", "S-h--3-2");
        hobby2DetailFields.put("HOBBY_DESCRIPTION", "S-h-3-2");

        hobbies1.add(createHobby(
            createDynamicObject("1926177721933438977", hobby2BasicFields),
            createDynamicObject("1926177721992159234", hobby2DetailFields)
        ));

        studentList.add(createStudentGroup(
            createDynamicObject("1926177721946021890", student1BasicFields),
            createDynamicObject("1926177721946021891", student1DetailFields),
            hobbies1
        ));

        // 第二个学生组
        Map<String, Object> student2BasicFields = new LinkedHashMap<>();
        student2BasicFields.put("NAME", "S-i-2");
        student2BasicFields.put("AGE", "3");
        student2BasicFields.put("SEX", "3");

        Map<String, Object> student2DetailFields = new LinkedHashMap<>();
        student2DetailFields.put("STUDENT_NAME", "S-2");
        student2DetailFields.put("STUDENT_NUM", "S-2");
        student2DetailFields.put("STUDENT_AGE", "3");

        // 第二个学生的爱好列表
        List<Map<String, Object>> hobbies2 = new ArrayList<>();

        Map<String, Object> hobby3BasicFields = new LinkedHashMap<>();
        hobby3BasicFields.put("NAME", "hi");
        hobby3BasicFields.put("MAIN", "hi");
        hobby3BasicFields.put("ADDRESS", "hi");
        hobby3BasicFields.put("COUNT", "3");

        Map<String, Object> hobby3DetailFields = new LinkedHashMap<>();
        hobby3DetailFields.put("HOBBY_NAME", "h");
        hobby3DetailFields.put("HOBBY_DESC", "h");
        hobby3DetailFields.put("HOBBY_DESCRIPTION", "h");

        hobbies2.add(createHobby(
            createDynamicObject("1926177721933438977", hobby3BasicFields),
            createDynamicObject("1926177721992159234", hobby3DetailFields)
        ));

        studentList.add(createStudentGroup(
            createDynamicObject("1926177721946021890", student2BasicFields),
            createDynamicObject("1926177721946021891", student2DetailFields),
            hobbies2
        ));

        data.put("list_1926177721946021891", studentList);

        return data;
    }

    // 使用 key-value map 创建动态对象（带后缀）
    public static Map<String, Object> createDynamicObject(String idSuffix, Map<String, Object> fields) {
        Map<String, Object> obj = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : fields.entrySet()) {
            String fullKey = entry.getKey() + (idSuffix != null && !idSuffix.isEmpty() ? "_" + idSuffix : "");
            obj.put(fullKey, entry.getValue());
        }
        return obj;
    }

    // 创建学生组
    private static Map<String, Object> createStudentGroup(
            Map<String, Object> basicInfo,
            Map<String, Object> studentDetails,
            List<Map<String, Object>> hobbies) {

        Map<String, Object> studentGroup = new LinkedHashMap<>();
        studentGroup.put("1926177721946021890", basicInfo);
        studentGroup.put("1926177721946021891", studentDetails);
        studentGroup.put("list_1926177721992159234", hobbies);
        return studentGroup;
    }

    // 创建爱好对象
    private static Map<String, Object> createHobby(
            Map<String, Object> basicInfo,
            Map<String, Object> hobbyInfo) {

        Map<String, Object> hobby = new LinkedHashMap<>();
        hobby.put("1926177721933438977", basicInfo);
        hobby.put("1926177721992159234", hobbyInfo);
        return hobby;
    }

}