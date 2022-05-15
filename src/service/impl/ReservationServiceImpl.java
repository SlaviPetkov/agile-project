package service.impl;

import exceptions.NonExistingEntityException;
import model.entity.Reservation;
import model.entity.Table;
import model.entity.User;
import repository.ReservationRepository;
import repository.TableRepository;

import service.ReservationService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

 class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final TableRepository tableRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository, TableRepository tableRepository) {
        this.reservationRepository = reservationRepository;
        this.tableRepository = tableRepository;
    }

    @Override
    public List<Reservation> findAll() {
        try {
            List<Reservation> all = reservationRepository.findAll();
            for (Reservation reservation : all) {
                reservation.setTable(tableRepository.findById(reservation.getTable().getId()));
            }
            return all;
        } catch (NonExistingEntityException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Reservation findById(Long id) {
        try {
            Reservation reservation = reservationRepository.findById(id);
            reservation.setTable(tableRepository.findById(reservation.getId()));
            return reservation;
        } catch (NonExistingEntityException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean insertReservation(Reservation entity) {
        return reservationRepository.insert(entity);
    }

    @Override
    public boolean updateReservation(Reservation entity) {
        try {
            return reservationRepository.update(entity);
        } catch (NonExistingEntityException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Reservation deleteById(Long id) {
        try {
            return reservationRepository.deleteById(id);
        } catch (NonExistingEntityException e) {
            e.printStackTrace();
        }
        return null;
    }

     @Override
     public List<Table> findAllTablesReservedByCurrentUser(Long userId) {
         List<Long> allTableId = reservationRepository.findAllTablesReservedByCurrentUser(userId);
         if(allTableId ==  null){
             return null;
         }

         List<Table> tables = new ArrayList<>();
         try {
         for (Long id : allTableId) {
             tables.add(tableRepository.findById(id));
         }
         } catch (NonExistingEntityException e) {
             e.printStackTrace();
         }
         return  tables;
     }

     @Override
     public Reservation findByUserAndTableAndStatus(Long tableId) {
         return reservationRepository.findByUserAndTableAndStatus(tableId);
     }

     @Override
     public List<Reservation> findAllNewReservations() {
         try {
             List<Reservation> all = reservationRepository.findAllNewReservations();
             for (Reservation reservation : all) {
                 reservation.setTable(tableRepository.findById(reservation.getTable().getId()));
             }
             return all;
         } catch (NonExistingEntityException e) {
             e.printStackTrace();
         }
         return null;
     }
     }
