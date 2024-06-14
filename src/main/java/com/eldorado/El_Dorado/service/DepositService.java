package com.eldorado.El_Dorado.service;


import com.eldorado.El_Dorado.domain.Deposit;
import com.eldorado.El_Dorado.exception.ResourceNotFoundException;
import com.eldorado.El_Dorado.repository.DepositRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DepositService {

    @Autowired
    private DepositRepository depositRepository;


    public ResponseEntity<?> getAllDeposits(Long accountId){
        return null;
    }

    public ResponseEntity<?> getById(Long depositId){
        return null;
    }

    public ResponseEntity<?> makeDeposit(Long accountId){
        return null;
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
            return new ResponseEntity(deposit, HttpStatus.OK);
        }).orElseThrow(() -> new ResourceNotFoundException("DepositId" + depositId + "not found."));
    }

    public ResponseEntity<?> deleteDeposit(Long depositId){
        //status 204 NO_CONTENT
        return null;
    }
    //timestamp of current datetime as string on creation of a deposit
    /**
     * Get withdrawals for a specific account
     *
     * Get withdrawal by id
     *
     * POST Create a withdrawal
     *
     * PUT Update a specified existing withdrawal
     *
     * DELETE Delete a specific existing withdrawal
     */
}
