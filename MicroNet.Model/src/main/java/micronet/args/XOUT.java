package micronet.args;

//*******************************************************************************************
//XOUT CLASS
//*******************************************************************************************
public class XOUT<T>
{
   public XOBJ<T> Obj = null;
   public XOUT(T value) 
   {
       Obj = new XOBJ<T>(value);
   }
   public XOUT() 
   {
     Obj = new XOBJ<T>();
   }  
   public XOUT<T> Out()
   {
       return(this);           
   }
   public XREF<T> Ref()
   {
       return(Obj.Ref());       
   }

};
