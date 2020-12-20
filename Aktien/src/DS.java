public class DS
{
    private String _date;
    private int _value;

    public int getValue()
    {
        return this._value;
    }
    public void setValue(int value)
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
    public DS(String date, int value)
    {
        this.setDate(date);
        this.setValue(value);
    }
}
