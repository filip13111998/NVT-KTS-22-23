package ftn.uns.ac.rs.NVTKTS20222023.service;

import ftn.uns.ac.rs.NVTKTS20222023.dto.request.MarkDTO;
import ftn.uns.ac.rs.NVTKTS20222023.model.*;
import ftn.uns.ac.rs.NVTKTS20222023.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class MarkService {

    @Autowired
    private MarkDriveRepository mdr;

    @Autowired
    private MarkRideRepository mrr;

    @Autowired
    private CommentRepository cm;

    @Autowired
    private CitizenRepository cr;

    @Autowired
    private RideRepository rr;

    public boolean mark(MarkDTO mdto) {

        Citizen citizen = cr.findByUsername(mdto.getUsername());

        Optional<Ride> optionalRide = rr.findById(mdto.getRideId());

        Ride ride = optionalRide.get();

        if (ride == null){
            return false;
        }
        if((new Date().getTime()) - ride.getStart()>(3 * 24 * 60 * 60 * 1000l)){
            return false;
        }

        Optional<Comment> optionalComment = cm.findAll().stream()
                .filter(c ->
                        c.getCitizen().getUsername().equals(mdto.getUsername())
                        && c.getRide().getId().equals(mdto.getRideId())
                )
                .findFirst();

        Comment comment = optionalComment.get();

        Optional<MarkRide> optionalMarkRide = mrr.findAll().stream()
                .filter(c ->
                        c.getCitizen().getUsername().equals(mdto.getUsername())
                                && c.getRide().getId().equals(mdto.getRideId())
                )
                .findFirst();

        MarkRide markRide = optionalMarkRide.get();

        Optional<MarkDriver> optionalMarkDriver = mdr.findAll().stream()
                .filter(c ->
                        c.getCitizen().getUsername().equals(mdto.getUsername())
                                && c.getDriver().getId().equals(ride.getDriver().getId())
                )
                .findFirst();

        MarkDriver markDriver = optionalMarkDriver.get();


        if(!mdto.getRideComment().equals("") && comment != null){
            comment.setComment(mdto.getRideComment());
        }else{
            Comment newComment = Comment.builder()
                    .citizen(citizen)
                    .ride(ride)
                    .comment(mdto.getRideComment())
                    .build();
            ride.getComments().add(comment);
            cm.save(newComment);
            rr.save(ride);
        }
        if(!mdto.getRideMark().equals(0) && markRide != null){
            markRide.setMark(mdto.getRideMark());
        }else{
            MarkRide newMark = MarkRide.builder()
                    .citizen(citizen)
                    .ride(ride)
                    .mark(mdto.getRideMark())
                    .build();
            ride.getMarks().add(newMark);
            mrr.save(newMark);
            rr.save(ride);
        }
        if(!mdto.getDriverMark().equals(0) && markDriver != null){
            markDriver.setMark(mdto.getDriverMark());
        }else{
            MarkDriver newMark = MarkDriver.builder()
                    .citizen(citizen)
                    .driver(ride.getDriver())
                    .mark(mdto.getRideMark())
                    .build();
            ride.getDriver().getMarks().add(newMark);
            mdr.save(newMark);
            rr.save(ride);
        }






        return true;
    }
}
