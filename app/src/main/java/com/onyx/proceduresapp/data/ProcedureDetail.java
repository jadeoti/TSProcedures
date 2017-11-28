package com.onyx.proceduresapp.data;

import java.util.List;

public class ProcedureDetail {
    private String id;

    private String name;

    private List<Phase> phases;

    private String icon;

    private String card;

    public ProcedureDetail(String id, String name, List<Phase> phases, String icon, String card) {
        this.id = id;
        this.name = name;
        this.phases = phases;
        this.icon = icon;
        this.card = card;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Phase> getPhases() {
        return phases;
    }

    public void setPhases(List<Phase> phases) {
        this.phases = phases;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }
}
