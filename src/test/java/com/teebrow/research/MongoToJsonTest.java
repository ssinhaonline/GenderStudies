package com.teebrow.research;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class MongoToJsonTest {

    public static void main(String[] args) throws IOException {
        JsonReader jsonReader = new JsonReader(new InputStreamReader(new FileInputStream("./data/profs.json"), "UTF-8"));
        JsonParser jsonParser = new JsonParser();

        jsonReader.beginArray();
        while (jsonReader.hasNext()) {
            JsonElement profElement = jsonParser.parse(jsonReader);

            JsonObject outputProfObject = new JsonObject();

            // Professor Identity Elements
            outputProfObject.addProperty("hexProfessorId",
                    profElement.getAsJsonObject().get("_id").getAsJsonObject().get("$oid").isJsonNull() ? null :
                            profElement.getAsJsonObject().get("_id").getAsJsonObject().get("$oid").getAsString());
            outputProfObject.addProperty("rmpProfessorId",
                    profElement.getAsJsonObject().get("id").isJsonNull() ? null :
                            profElement.getAsJsonObject().get("id").getAsString());
            outputProfObject.addProperty("professorName",
                    profElement.getAsJsonObject().get("name").isJsonNull() ? null :
                            profElement.getAsJsonObject().get("name").getAsString());
            outputProfObject.addProperty("professorGender",
                    profElement.getAsJsonObject().get("gender").isJsonNull() ? null :
                            profElement.getAsJsonObject().get("gender").getAsString());

            // Professor Aggregated Rating Elements
            outputProfObject.addProperty("professorAggregatedRatingsOverallQuality",
                    profElement.getAsJsonObject().get("ratings").isJsonNull() ||
                            profElement.getAsJsonObject().get("ratings").getAsJsonObject().get("overall-quality").isJsonNull() ? null :
                            profElement.getAsJsonObject().get("ratings").getAsJsonObject().get("overall-quality").getAsString());
            outputProfObject.addProperty("professorAggregatedRatingsHelpfulness",
                    profElement.getAsJsonObject().get("ratings").isJsonNull() ||
                            profElement.getAsJsonObject().get("ratings").getAsJsonObject().get("helpfulness").isJsonNull() ? null :
                            profElement.getAsJsonObject().get("ratings").getAsJsonObject().get("helpfulness").getAsString());
            outputProfObject.addProperty("professorAggregatedRatingsClarity",
                    profElement.getAsJsonObject().get("ratings").isJsonNull() ||
                            profElement.getAsJsonObject().get("ratings").getAsJsonObject().get("clarity").isJsonNull() ? null :
                    profElement.getAsJsonObject().get("ratings").getAsJsonObject().get("clarity").getAsString());
            outputProfObject.addProperty("professorAggregatedRatingsEasiness",
                    profElement.getAsJsonObject().get("ratings").isJsonNull() ||
                            profElement.getAsJsonObject().get("ratings").getAsJsonObject().get("easiness").isJsonNull() ? null :
                    profElement.getAsJsonObject().get("ratings").getAsJsonObject().get("easiness").getAsString());
            outputProfObject.addProperty("professorAggregatedRatingsAverageGradeReceived",
                    profElement.getAsJsonObject().get("ratings").isJsonNull() ||
                            profElement.getAsJsonObject().get("ratings").getAsJsonObject().get("avg-grade-received").isJsonNull() ? null :
                    profElement.getAsJsonObject().get("ratings").getAsJsonObject().get("avg-grade-received").getAsString());

            // Professor Top 20 Tags Aggregation
            JsonArray tagsArray = new JsonArray();
            for (JsonElement tagElement : profElement.getAsJsonObject().get("top 20 tags").getAsJsonArray()) {
                JsonObject outputTagObject = new JsonObject();
                outputTagObject.addProperty("tagScore",
                        tagElement.getAsJsonObject().get("tag-score").getAsString());
                outputTagObject.addProperty("tagName",
                        tagElement.getAsJsonObject().get("tag-name").getAsString());
                tagsArray.add(outputTagObject);
            }
            outputProfObject.add("professorAggregatedRatingsTopTwentyTags", tagsArray);

            // Professor Details
            outputProfObject.addProperty("professorDetailCity",
                    profElement.getAsJsonObject().get("ratings").isJsonNull() ||
                            profElement.getAsJsonObject().get("details").getAsJsonObject().get("city").isJsonNull() ? null :
                            profElement.getAsJsonObject().get("details").getAsJsonObject().get("city").getAsString());
            outputProfObject.addProperty("professorDetailUniversity",
                    profElement.getAsJsonObject().get("ratings").isJsonNull() ||
                            profElement.getAsJsonObject().get("details").getAsJsonObject().get("university").isJsonNull() ? null :
                            profElement.getAsJsonObject().get("details").getAsJsonObject().get("university").getAsString());
            outputProfObject.addProperty("professorDetailState",
                    profElement.getAsJsonObject().get("ratings").isJsonNull() ||
                            profElement.getAsJsonObject().get("details").getAsJsonObject().get("state").isJsonNull() ? null :
                            profElement.getAsJsonObject().get("details").getAsJsonObject().get("state").getAsString());
            outputProfObject.addProperty("professorDetailDepartment",
                    profElement.getAsJsonObject().get("ratings").isJsonNull() ||
                            profElement.getAsJsonObject().get("details").getAsJsonObject().get("department").isJsonNull() ? null :
                            profElement.getAsJsonObject().get("details").getAsJsonObject().get("department").getAsString());

            // Professor Comments Loop
            for (JsonElement commentElement : profElement.getAsJsonObject().get("all comments").getAsJsonArray()) {
                JsonObject outputProfLoopObject = outputProfObject.deepCopy();

                // Comment Open Ended Response
                outputProfLoopObject.addProperty("commentDescriptionText",
                        commentElement.getAsJsonObject().get("rComments").isJsonNull() ? null :
                                commentElement.getAsJsonObject().get("rComments").getAsString());
                outputProfLoopObject.addProperty("commentDescriptionDate",
                        commentElement.getAsJsonObject().get("rDate").isJsonNull() ? null :
                                commentElement.getAsJsonObject().get("rDate").getAsString());

                // Comment Ratings Based Response
                outputProfLoopObject.addProperty("commentRatingsQualityGrade",
                        commentElement.getAsJsonObject().get("quality").isJsonNull() ? null :
                                commentElement.getAsJsonObject().get("quality").getAsString());
                outputProfLoopObject.addProperty("commentRatingsHelpfulness",
                        commentElement.getAsJsonObject().get("rHelpful").isJsonNull() ? null :
                                commentElement.getAsJsonObject().get("rHelpful").getAsString());
                outputProfLoopObject.addProperty("commentRatingsClarity",
                        commentElement.getAsJsonObject().get("rClarity").isJsonNull() ? null :
                                commentElement.getAsJsonObject().get("rClarity").getAsString());
                outputProfLoopObject.addProperty("commentRatingsEasiness",
                        commentElement.getAsJsonObject().get("rEasy").isJsonNull() ? null :
                                commentElement.getAsJsonObject().get("rEasy").getAsString());
                outputProfLoopObject.addProperty("commentRatingsHelpfulnessGrade",
                        commentElement.getAsJsonObject().get("helpColor").isJsonNull() ? null :
                                commentElement.getAsJsonObject().get("helpColor").getAsString());
                outputProfLoopObject.addProperty("commentRatingsClarityGrade",
                        commentElement.getAsJsonObject().get("clarityColor").isJsonNull() ? null :
                                commentElement.getAsJsonObject().get("clarityColor").getAsString());
                outputProfLoopObject.addProperty("commentRatingsEasinessGrade",
                        commentElement.getAsJsonObject().get("easyColor").isJsonNull() ? null :
                                commentElement.getAsJsonObject().get("easyColor").getAsString());
                outputProfLoopObject.add("commentRatingsTags",
                        commentElement.getAsJsonObject().get("teacherRatingTags").isJsonNull() ? null :
                                commentElement.getAsJsonObject().get("teacherRatingTags").getAsJsonArray());


                // Comment Course Based Response
                outputProfLoopObject.addProperty("commentResponseRelevantCourse",
                        commentElement.getAsJsonObject().get("rClass").isJsonNull() ? null :
                                commentElement.getAsJsonObject().get("rClass").getAsString());
                outputProfLoopObject.addProperty("commentResponseTextbookUsed",
                        commentElement.getAsJsonObject().get("rTextBookUse").isJsonNull() ? null :
                                commentElement.getAsJsonObject().get("rTextBookUse").getAsString());
                outputProfLoopObject.addProperty("commentResponseReceivedGrade",
                        commentElement.getAsJsonObject().get("teacherGrade").isJsonNull() ? null :
                                commentElement.getAsJsonObject().get("teacherGrade").getAsString());
                outputProfLoopObject.addProperty("commentResponseInterest",
                        commentElement.getAsJsonObject().get("rInterest").isJsonNull() ? null :
                                commentElement.getAsJsonObject().get("rInterest").getAsString());
                outputProfLoopObject.addProperty("commentResponseAttendance",
                        commentElement.getAsJsonObject().get("attendance").isJsonNull() ? null :
                                commentElement.getAsJsonObject().get("attendance").getAsString());
                outputProfLoopObject.addProperty("commentResponseTakenForCredit",
                        commentElement.getAsJsonObject().get("takenForCredit").isJsonNull() ? null :
                                commentElement.getAsJsonObject().get("takenForCredit").getAsString());
                outputProfLoopObject.addProperty("commentResponseOnlineClass",
                        commentElement.getAsJsonObject().get("onlineClass").isJsonNull() ? null :
                                commentElement.getAsJsonObject().get("onlineClass").getAsString());

                // Comment Other Details
                outputProfLoopObject.addProperty("commentMiscellaneousErrorMessage",
                        commentElement.getAsJsonObject().get("rErrorMsg").isJsonNull() ? null :
                                commentElement.getAsJsonObject().get("rErrorMsg").getAsString());
                outputProfLoopObject.addProperty("commentMiscellaneousUsefulGrouping",
                        commentElement.getAsJsonObject().get("usefulGrouping").isJsonNull() ? null :
                                commentElement.getAsJsonObject().get("usefulGrouping").getAsString());
                outputProfLoopObject.addProperty("commentMiscellaneousUnUsefulGrouping",
                        commentElement.getAsJsonObject().get("unUsefulGrouping").isJsonNull() ? null :
                                commentElement.getAsJsonObject().get("unUsefulGrouping").getAsString());
                outputProfLoopObject.addProperty("commentMiscellaneousSId",
                        commentElement.getAsJsonObject().get("sId").isJsonNull() ? null :
                                commentElement.getAsJsonObject().get("sId").getAsString());
                outputProfLoopObject.addProperty("commentMiscellaneousId",
                        commentElement.getAsJsonObject().get("id").isJsonNull() ? null :
                                commentElement.getAsJsonObject().get("id").getAsString());
                outputProfLoopObject.addProperty("commentMiscellaneousStatus",
                        commentElement.getAsJsonObject().get("rStatus").isJsonNull() ? null :
                                commentElement.getAsJsonObject().get("rStatus").getAsString());
                outputProfLoopObject.addProperty("commentMiscellaneousFoundHelpfulCount",
                        commentElement.getAsJsonObject().get("helpCount").isJsonNull() ? null :
                                commentElement.getAsJsonObject().get("helpCount").getAsString());
                outputProfLoopObject.addProperty("commentMiscellaneousFoundNotHelpful",
                        commentElement.getAsJsonObject().get("notHelpCount").isJsonNull() ? null :
                                commentElement.getAsJsonObject().get("notHelpCount").getAsString());
                outputProfLoopObject.addProperty("commentMiscellaneousTeacher",
                        commentElement.getAsJsonObject().get("teacher").isJsonNull() ? null :
                                commentElement.getAsJsonObject().get("teacher").getAsString());

                // Write individual Professor_Comment combination to file ./data/documents/<hexProfessorId>_<commentMiscellaneousId>.json
                FileWriter writer = new FileWriter("./data/documents/" +
                        outputProfLoopObject.get("hexProfessorId").getAsString() + "_" +
                        outputProfLoopObject.get("commentMiscellaneousId").getAsString() + ".json");
                writer.write(new GsonBuilder().setPrettyPrinting().create().toJson(outputProfLoopObject));
                writer.close();
            }
        }
        jsonReader.close();
    }
}
