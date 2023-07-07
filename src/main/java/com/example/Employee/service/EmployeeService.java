package com.example.Employee.service;

import com.example.Employee.dto.EmployeeDTO;
import com.example.Employee.entity.Employee;
import com.example.Employee.repo.EmployeeRepo;
import com.example.Employee.util.VarList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private ModelMapper modelMapper;     //  used for object mapping or conversion between different object types

    public String saveEmp(EmployeeDTO employeeDTO){
        if(employeeRepo.existsById(employeeDTO.getEmpId())){
            return VarList.RSP_DUPLICATED;
        }else{
            employeeRepo.save(modelMapper.map(employeeDTO, Employee.class));
            return VarList.RSP_SUCCESS;
        }
    }

    public String updateEmp(EmployeeDTO employeeDTO){
        if(employeeRepo.existsById(employeeDTO.getEmpId())){    // update if there is an employee
            employeeRepo.save(modelMapper.map(employeeDTO, Employee.class));    // save function used
            return VarList.RSP_SUCCESS;
        }else{
            return  VarList.RSP_NO_DATA_FOUND;
        }
    }

    public List<EmployeeDTO> getAllEmp () {
        List<Employee> employeeList = employeeRepo.findAll();   // findAll function
        return modelMapper.map(employeeList,new TypeToken<ArrayList<EmployeeDTO>>(){
        }.getType());
    }

    public EmployeeDTO searchEmp(int empId){
        if(employeeRepo.existsById(empId)){
            Employee  employee =employeeRepo.findById(empId).orElse(null);
            return  modelMapper.map(employee,EmployeeDTO.class);
        }else{
            return null;
        }
    }

    public String deleteEmp(int empId){
        if(employeeRepo.existsById(empId)){
            employeeRepo.deleteById(empId);
            return VarList.RSP_SUCCESS;
        }else{
            return VarList.RSP_NO_DATA_FOUND;
        }
    }
}
