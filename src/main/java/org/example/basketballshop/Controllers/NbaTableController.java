package org.example.basketballshop.Controllers;

import org.example.basketballshop.DTO.NbaTableResponse.NbaTableResponse;
import org.example.basketballshop.Services.NbaTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NbaTableController {
    @Autowired
    private NbaTableService nbaTableService;

    @GetMapping("/tournamentScore")
    public String getNbaTablePage(@RequestParam(required = false) String conference, Model model) {
        NbaTableResponse[] tableResponses = nbaTableService.getNbaTableData(conference);

        model.addAttribute("tableData", tableResponses);
        return "nba_tournament_page";
    }
}
