    package com.elevana_api.controller;

import com.elevana_api.service.DoomsdayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/admin"})
public class DoomsdayController {
   @Autowired
   private DoomsdayService doomsdayService;

   @DeleteMapping({"/doomsday"})
   public String resetEverythingExceptUsers() {
      this.doomsdayService.nuke();
      return "\ud83d\udd25 Doomsday executed: All non-user data has been deleted!";
   }
}
