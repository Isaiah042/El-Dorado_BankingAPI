package com.eldorado.El_Dorado.service;


import com.eldorado.El_Dorado.domain.Deposit;
import com.eldorado.El_Dorado.exception.ResourceNotFoundException;
import com.eldorado.El_Dorado.repository.DepositRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepositService {

    private final DepositRepository depositRepository;

    @Autowired
    public DepositService(DepositRepository depositRepository){
        this.depositRepository = depositRepository;
    }



    public ResponseEntity<?> getAllDeposits(Long accountId){
        List<Deposit> allDeposits = depositRepository.findByAccount(accountId);
        if(allDeposits == null){
            throw new ResourceNotFoundException("Account not found");
        }else
            return new ResponseEntity<>(allDeposits, HttpStatus.OK);
    }

    public ResponseEntity<?> getById(Long depositId){
        Deposit foundDeposit = depositRepository.findById(depositId).orElse(null);
        if(foundDeposit == null){
            throw new ResourceNotFoundException("Error fetching deposit with id");
        } else
            return new ResponseEntity<>(foundDeposit, HttpStatus.OK);
    }

    public ResponseEntity<?> makeDeposit(Long accountId, Deposit deposit){
        Deposit newDeposit = depositRepository.save(deposit);
        return new ResponseEntity<>(newDeposit, HttpStatus.CREATED);
        //for deposit type DEPOSIT
    }

    public ResponseEntity<?> updateDeposit(Long depositId, Deposit depositRequest){
        return depositRepository.findById(depositId).map(deposit ->{
            deposit.setType(depositRequest.getType());
            deposit.setTransaction_date(depositRequest.getTransaction_date());
            deposit.setStatus(depositRequest.getStatus());
            deposit.setPayee_id(depositRequest.getPayee_id());
            deposit.setMedium(depositRequest.getMedium());
            deposit.setAmount(depositRequest.getAmount());
            deposit.setDescription(depositRequest.getDescription());
            depositRepository.save(deposit);
            return new ResponseEntity(deposit, HttpStatus.ACCEPTED);
        }).orElseThrow(() -> new ResourceNotFoundException("Deposit ID does not exist."));
    }

    /**
     * deleting deposit should reverse the process and result in a withdrawal
     * @param depositId
     * @return
     */
    public ResponseEntity<?> deleteDeposit(Long depositId){
        Deposit depositToDelete = depositRepository.findById(depositId).orElse(null);
        if(depositToDelete == null){
            throw new ResourceNotFoundException("This id does not exist in deposits");
        }else
            //perform the withdrawal here
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
