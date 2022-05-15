package service.impl;

import exceptions.NonExistingEntityException;
import model.entity.Table;
import model.enums.TableStatusEnum;
import repository.OrderRepository;
import repository.ReservationRepository;
import repository.TableRepository;

import service.OrderService;
import service.TableService;

import java.util.List;

 class TableServiceImpl implements TableService {
    private final TableRepository tableRepository ;
    private final ReservationRepository reservationRepository;
    private final OrderRepository orderRepository;
    private final OrderService orderService;

    public TableServiceImpl(TableRepository tableRepository, ReservationRepository reservationRepository, OrderRepository orderRepository, OrderService orderService) {
        this.tableRepository = tableRepository;
        this.reservationRepository = reservationRepository;
        this.orderRepository = orderRepository;
        this.orderService = orderService;
    }

    @Override
    public List<Table> findAll() {
        List<Table> found = tableRepository.findAll();
        try {
        for (Table table : found) {
            if(table.getStatus().equals(TableStatusEnum.RESERVED)){
                table.setReservation(reservationRepository.findById(table.getReservation().getId()));
            }
            if(table.getStatus().equals(TableStatusEnum.ACTIVE)){
                table.setOrder(orderService.findById(table.getOrder().getId()));
            }
        }
        } catch (NonExistingEntityException e) {
            e.printStackTrace();
        }
        return found;

    }

    @Override
    public Table findById(Long id) {
        try {
            Table found = tableRepository.findById(id);
            if(found.getStatus().equals(TableStatusEnum.RESERVED)){
            found.setReservation(reservationRepository.findById(found.getReservation().getId()));
            }
            if(found.getStatus().equals(TableStatusEnum.ACTIVE)){
                found.setOrder(orderService.findById(found.getOrder().getId()));
            }
            return found;
        } catch (NonExistingEntityException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean insertTable(Table table) {
        return tableRepository.insert(table);
    }

    @Override
    public boolean updateTable(Table table) {
        try {
            return tableRepository.update(table);
        } catch (NonExistingEntityException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Table deleteById(Long id) {
        try {
            return tableRepository.deleteById(id);
        } catch (NonExistingEntityException e) {
            e.printStackTrace();
        }
        return null;
    }

     @Override
     public List<Table> findAllFreeTables() {
         return tableRepository.findAllFreeTables();
     }

     @Override
     public Table findTableByCode(String tableCode) {
         Table found = tableRepository.findByCode(tableCode);
         try {
         if(found.getStatus().equals(TableStatusEnum.RESERVED)){
             found.setReservation(reservationRepository.findById(found.getReservation().getId()));
         }
         if(found.getStatus().equals(TableStatusEnum.ACTIVE)){
                 found.setOrder(orderService.findById(found.getOrder().getId()));
             }
         } catch (NonExistingEntityException e) {
             e.printStackTrace();
         }
         return found;
     }

     @Override
     public boolean updateTableToReserved(Table table) {
         return tableRepository.updateTableToReserved(table);
     }

     @Override
     public boolean updateTableToActive(Table table) {
        if(table.getReservation()!=null) {
            reservationRepository.updateToFinished(table.getReservation());
        }
         return tableRepository.updateTableToActive(table);
     }

     @Override
     public void updateTableToFree(Table table) {
         tableRepository.updateToFree(table);
     }


 }
