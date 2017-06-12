package micronet.args;

//*******************************************************************************************
//XREF CLASS
//*******************************************************************************************

public class XREF<T>
{
     public XOBJ<T> Obj = null;
     public XREF(T value) 
     {
         Obj = new XOBJ<T>(value);
     }
     public XREF() 
     {
       Obj = new XOBJ<T>();
     }  
     public XOUT<T> Out()
     {
         return(Obj.Out());       
     }
     public XREF<T> Ref()
     {
         return(this);
     }       
};


