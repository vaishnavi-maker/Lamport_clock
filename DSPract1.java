
package DSPract1;
import java.util.*;
public class DSPract1 {
 public static void main(String[] args) {
 Scanner sc=new Scanner(System.in);
 System.out.println("Enter no. of processes: ");
 int np=sc.nextInt();
 System.out.println("Enter delay value: ");
 int delay=sc.nextInt();
 L_Process p[]=new L_Process[np];
 L_Event e[][]=new L_Event[np][];
 HashMap<String,Integer> hm=new HashMap<>();
 for(int ii=0;ii<np;ii++)
 {
 p[ii]=new L_Process();
 p[ii].clock=0;
 System.out.println("Enter no. of events in P"+(ii+1)+" :");
 int ne=sc.nextInt();
 e[ii]=new L_Event[ne];
 for(int j=0;j<ne;j++)
 {
 e[ii][j]=new L_Event();
 System.out.println("Enter the event name : ");
 e[ii][j].name=sc.next();
 System.out.println("Enter its status( for example send , receive or idle) ");
 e[ii][j].status=sc.next();
 if(e[ii][j].status.equals("receive"))
 {
 System.out.println("Enter the name of sending event ");
 e[ii][j].s_event=sc.next();//sendingevent
 }
 }
 }
 int i=0;
 boolean signal1=false,signal2=false;
 while(i<np)
 {
 for(int j=0;j<e[i].length;j++)
 {
 if(e[i][j].t_stamp==0)
 {
 if(e[i][j].status.equals("idle"))
 {
 p[i].clock+=1;
 e[i][j].t_stamp=p[i].clock;
 hm.put(e[i][j].name,e[i][j].t_stamp);
 }
 else if(e[i][j].status.equals("send"))
 {
 p[i].clock+=1;
 e[i][j].t_stamp=p[i].clock;
 e[i][j].msg=p[i].clock;
 hm.put(e[i][j].name,e[i][j].t_stamp);
 }
 else
 {
 String se=e[i][j].s_event;
 if(hm.containsKey(se))
 {
 p[i].clock=Math.max(p[i].clock+1,hm.get(se)+1);
 e[i][j].t_stamp=p[i].clock;
 hm.put(e[i][j].name,e[i][j].t_stamp);
 }
 else
 break;
 }
 }
 }
 for(int j=0;j<e[i].length;j++)
 {
 if(e[i][j].t_stamp==0)
 signal1=true;
 }
 if(signal1==false)
 p[i].eventsOver=true;
 signal1=false;
 for(int j=0;j<np;j++)
 {
 if(p[j].eventsOver==false)
 signal2=true;
 }
 if(signal2==false)
 break;
 signal2=false;
 if(i==np-1)
 i=0;
 else
 i+=1;
 }
 System.out.println("-----------------------------------------------------------");
 System.out.println("calculate using Lamport clock ");
 for(int j=0;j<np;j++)
 {
 System.out.print("P"+(j+1)+": ");
 for(int k=0;k<e[j].length;k++)
 {
 System.out.print(e[j][k].name+"["+e[j][k].t_stamp+"] ");
 }
 System.out.println();
 }
 System.out.println("-----------------------------------------------------------");
 }
}
class L_Event
{
 String name="",status="",s_event="";
 int t_stamp=0,msg=0;
}
class L_Process
{
 int clock=0;
 Boolean eventsOver=false;
}