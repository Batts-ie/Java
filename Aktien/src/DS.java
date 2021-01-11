public class DS
{
    private String _date;
    private double _value;

    public double getValue(){return this._value;}
    public void setValue(double value)
    {
        if(value > 0) this._value = value;
    }
    public String getDate()
    {
        return this._date;
    }
    public void setDate(String date)
    {
        this._date = date;
    }





    //public DS("", 0){}
    public DS(String date, double value)
    {
        this.setDate(date);
        this.setValue(value);
    }
}
