package com.example.webservice;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Path("/person")
@Produces(MediaType.APPLICATION_XML)
@Consumes(MediaType.APPLICATION_XML)
public class PersonServiceImpl implements PersonService {

    private static Map<Integer,Person> persons = new HashMap<Integer,Person>();

    static {
        Person person = new Person();
        person.setAge(35);
        person.setName("Sumit");
        person.setId(101);
        persons.put(101,person);
    }

    @POST
    @Path("/add")
    @Override public Response addPerson(Person p) {
        Response response = new Response();
        if(persons.get(p.getId()) != null){
            response.setStatus(false);
            response.setMessage("Person Already Exists");
            return response;
        }
        persons.put(p.getId(), p);
        response.setStatus(true);
        response.setMessage("Person created successfully");
        return response;
    }

    @GET
    @Path("/{id}/delete")
    @Override public Response deletePerson(@PathParam("id") int id) {
        Response response = new Response();
        if(persons.get(id) == null){
            response.setStatus(false);
            response.setMessage("Person Doesn't Exists");
            return response;
        }
        persons.remove(id);
        response.setStatus(true);
        response.setMessage("Person deleted successfully");
        return response;
    }

    @GET
    @Path("/{id}/getDummy")
    @Override public Person getPerson(@PathParam("id") int id) {
        return persons.get(id);
    }

    @GET
    @Path("/getAll")
    @Override public Person[] getAllPersons() {
        Set<Integer> ids = persons.keySet();
        Person[] p = new Person[ids.size()];
        int i=0;
        for(Integer id : ids){
            p[i] = persons.get(id);
            i++;
        }
        return p;
    }
}
