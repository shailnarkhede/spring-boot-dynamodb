package com.example.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.example.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeRepository {

    private final DynamoDBMapper dynamoDBMapper;

    @Autowired
    public EmployeeRepository(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public Employee save(Employee employee) {
       dynamoDBMapper.save(employee);
       return employee;
    }

    public Employee get(String id) {
        return dynamoDBMapper.load(Employee.class, id);
    }

    public Employee update(String id, Employee employee) {
        dynamoDBMapper.save(employee, getSaveExpression(id));
        return employee;
    }

    public String delete(String id) {
        dynamoDBMapper.delete(dynamoDBMapper.load(Employee.class, id));
        return "Employee with id "+ id + " deleted sucessfully";
    }

    private DynamoDBSaveExpression getSaveExpression(String id) {
        return new DynamoDBSaveExpression()
                .withExpectedEntry("id",
                        new ExpectedAttributeValue(
                                new AttributeValue().withS(id)
                        ));
    }


}
