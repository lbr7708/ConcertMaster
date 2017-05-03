package team_f.domain.entities;

import team_f.domain.enums.InstrumentType;
import team_f.domain.helper.TextHelper;
import team_f.domain.interfaces.DomainEntity;
import java.util.List;

public class Instrumentation implements DomainEntity {
    private int _instrumentationID;

    //WoodInstrumentation
    private int _flute;
    private int _oboe;
    private int _clarinet;
    private int _bassoon;

    //StringInstrumentation
    private int _violin1;
    private int _violin2;
    private int _viola;
    private int _violincello;
    private int _doublebass;

    //BrassInstrumentation
    private int _horn;
    private int _trumpet;
    private int _trombone;
    private int _tube;

    //PercussionInstrumentation
    private int _kettledrum;
    private int _percussion;
    private int _harp;

    // specialInstrumentation
    private List<SpecialInstrumentation> special;

    public int getInstrumentationID() {
        return _instrumentationID;
    }

    public int getFlute() {
        return _flute;
    }

    public int getOboe() {
        return _oboe;
    }

    public int getClarinet() {
        return _clarinet;
    }

    public int getBassoon() {
        return _bassoon;
    }

    public int getViolin1() {
        return _violin1;
    }

    public int getViolin2() {
        return _violin2;
    }

    public int getViola() {
        return _viola;
    }

    public int getViolincello() {
        return _violincello;
    }

    public int getDoublebass() {
        return _doublebass;
    }

    public int getHorn() {
        return _horn;
    }

    public int getTrumpet() {
        return _trumpet;
    }

    public int getTrombone() {
        return _trombone;
    }

    public int getTube() {
        return _tube;
    }

    public int getKettledrum() {
        return _kettledrum;
    }

    public int getPercussion() {
        return _percussion;
    }

    public void setClarinet(int clarinet) {
        _clarinet = clarinet;
    }

    public void setInstrumentationID(int instrumentationID) {
        _instrumentationID = instrumentationID;
    }

    public void setFlute(int flute) {
        _flute = flute;
    }

    public void setOboe(int oboe) {
        _oboe = oboe;
    }

    public void setBassoon(int bassoon) {
        _bassoon = bassoon;
    }

    public void setViolin1(int violin1) {
        _violin1 = violin1;
    }

    public void setViolin2(int violin2) {
        _violin2 = violin2;
    }

    public void setViola(int viola) {
        _viola = viola;
    }

    public void setViolincello(int violincello) {
        _violincello = violincello;
    }

    public void setDoublebass(int doublebass) {
        _doublebass = doublebass;
    }

    public void setHorn(int horn) {
        _horn = horn;
    }

    public void setTrumpet(int trumpet) {
        _trumpet = trumpet;
    }

    public void setTrombone(int trombone) {
        _trombone = trombone;
    }

    public void setTube(int tube) {
        _tube = tube;
    }

    public void setKettledrum(int kettledrum) {
        _kettledrum = kettledrum;
    }

    public void setPercussion(int percussion) {
        _percussion = percussion;
    }

    public int getHarp() {
        return _harp;
    }

    public void setHarp(int harp) {
        _harp = harp;
    }

    public String getWoodInstrumentationText() {
        return "Wood: " + TextHelper.getSeparatedText('/', getFlute(), getOboe(), getClarinet(), getBassoon());
    }

    public String getStringInstrumentationText() {
        return "String: " + TextHelper.getSeparatedText('/', getViolin1(), getViolin2(), getViola(), getViolincello(), getDoublebass());
    }

    public String getBrassInstrumentiaton() {
        return "Brass: " + TextHelper.getSeparatedText('/', getHorn(), getTrumpet(), getTrombone(), getTube());
    }

    public String getPercussionInstrumentation() {
        return "Percussion: " + TextHelper.getSeparatedText('/', getKettledrum(), getPercussion(), getHarp());
    }

    //TODO: specialinstrumentation
    public String getSpecialInstrumentation() {
        return "Special: " + TextHelper.getTextFromString('+', "","","");
    }

    public String getInstrumentationText() {
        StringBuilder text = new StringBuilder();
        text.append(getWoodInstrumentationText());
        text.append('\n');
        text.append(getStringInstrumentationText());
        text.append('\n');
        text.append(getBrassInstrumentiaton());
        text.append('\n');
        text.append(getPercussionInstrumentation());
        text.append('\n');
        text.append(getSpecialInstrumentation());


        return text.toString();
    }

    public void addToSpecial(String instrument, int instrumentCount, String section) {
        SpecialInstrumentation specialInstrumentation = new SpecialInstrumentation();
        specialInstrumentation.setSpecialInstrument(instrument);
        specialInstrumentation.setSpecialInstrumentCount(instrumentCount);
        specialInstrumentation.setSectionType(section);
        special.add(specialInstrumentation);
    }

    public void addToInstrumentations(Instrumentation instrumentation) {
        setFlute(getFlute() + instrumentation.getFlute());
        setOboe(getOboe() + instrumentation.getOboe());
        setClarinet(getClarinet() + instrumentation.getClarinet());
        setBassoon(getBassoon() + instrumentation.getBassoon());

        setViolin1(getViolin1() + instrumentation.getViolin1());
        setViolin2(getViolin2() + instrumentation.getViolin2());
        setViola(getViola() + instrumentation.getViola());
        setViolincello(getViolincello() + instrumentation.getViolincello());
        setDoublebass(getDoublebass() + instrumentation.getDoublebass());

        setHorn(getHorn() + instrumentation.getHorn());
        setTrumpet(getTrumpet() + instrumentation.getTrumpet());
        setTrombone(getTrombone() + instrumentation.getTrombone());
        setTube(getTube() + instrumentation.getTube());

        setKettledrum(getKettledrum() + instrumentation.getKettledrum());
        setPercussion(getPercussion() + instrumentation.getPercussion());
        setHarp(getHarp() + instrumentation.getHarp());
    }

    public int getByInstrumentType(InstrumentType instrumentType) {
        switch (instrumentType) {
            case FLUTE:
                return getFlute();
            case OBOE:
                return getOboe();
            case CLARINET:
                return getClarinet();
            case BASSOON:
                return getBassoon();
            case FIRSTVIOLIN:
                return getViolin1();
            case SECONDVIOLIN:
                return getViolin2();
            case VIOLA:
                return getViola();
            case VIOLONCELLO:
                return getViolincello();
            case DOUBLEBASS:
                return getDoublebass();
            case HORN:
                return getHorn();
            case TRUMPET:
                return getTrumpet();
            case TROMBONE:
                return getTrombone();
            case TUBE:
                return getTube();
            case KETTLEDRUM:
                return getKettledrum();
            case PERCUSSION:
                return getPercussion();
            case HARP:
                return getHarp();
        }

        return -1;
    }
}
