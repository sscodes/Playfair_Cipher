import java.io.*;
class Playfair_Cipher
{
    public static void main(String args[])throws IOException
    {
        Playfair_Cipher p=new Playfair_Cipher();
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));        
        System.out.print("Enter the plaintext : ");
        String pt=br.readLine();
        System.out.print("Enter the key : ");
        String key=br.readLine();
        char[][] T = new char[5][5];
        for(int i=0; i<5; i++)
        {
            for(int j=0; j<5; j++)
            {
                T[i][j]=' ';
            }
        }
        String a=p.key_fill(key, T);
        int var=p.rest_fill(T, a);
        p.gen_cyptxt(pt, T, var);
    }
    public String key_fill(String K, char[][] table)
    {
        int c=0, x=0, ni=0, nj=0, z=0;
        K=K.toUpperCase();
        int l=K.length();
        String s="", key1="";
        for(int i=0; i<l-1; i++)    
        {
            for(int j=i+1; j<l; j++)
            {
                if(K.charAt(i)==K.charAt(j))
                {
                    c++;
                    break;
                }
            }
        }
        if(c==1)    
        {
            for(int i=l-1; i>0;i--) 
            {
                for(int j=i-1;j>=0;j--)
                {
                    if(K.charAt(i)==K.charAt(j))
                    x++;
                }
                if(x==0)
                s=s+K.charAt(i);
                x=0;
            }
            int len =s.length();
            x=0;
            for(int i=0; i<len; i++)    
            {
                if(K.charAt(0)==s.charAt(i))
                x++;
            }
            if(x==0)    
            s=s+K.charAt(0);
            len=s.length();
            for(int i=0; i<len; i++)
            key1=s.charAt(i)+key1;
            len=key1.length();
            
            int u=0, v=0;
            for(int i=0; i<len; i++)
            {
                if(key1.charAt(i)=='i')
                u++;
                else if(key1.charAt(i)=='j')
                v++;
            }
            if(u>0 && v>0)
            {
                String str="";
                for(int i=0; i<len; i++)
                {
                    if(key1.charAt(i)!='j')
                    str=str+key1.charAt(i);
                    else
                    continue;
                }
                key1=str;
            }
            len=key1.length();
            ni=len/5;
            nj=len%5;
            if(ni>0)
            {
                for(int i=0;i<ni;i++)
                {
                    for(int j=0; j<5; j++)
                    {
                        table[i][j]=key1.charAt(z);
                        z++;
                    }
                }
                for(int i=0; i<nj; i++)
                {
                    table[ni][nj]=key1.charAt(z);
                    z++;
                }
            }
            else
            {
                for(int i=0; i<nj; i++)
                {
                    table[0][i]=key1.charAt(i);
                }
            }
            return key1;
        }
        else 
        {
            int u=0, v=0;
            for(int i=0; i<l; i++)
            {
                if(K.charAt(i)=='i')
                u++;
                else if(K.charAt(i)=='j')
                v++;
            }
            if(u>0 && v>0)
            {
                String str="";
                for(int i=0; i<l; i++)
                {
                    if(K.charAt(i)!='j')
                    str=str+K.charAt(i);
                    else
                    continue;
                }
                K=str;
            }
            l=K.length();
            ni=l/5;
            nj=l%5;
            if(ni>0)
            {
                for(int i=0; i<ni; i++)
                {
                    for(int j=0;j<5;j++)
                    {
                        table[i][j]=K.charAt(z);
                        z++;
                    }
                }
                for(int i=0; i<nj; i++)
                {
                    table[ni][i]=K.charAt(z);
                    z++;
                }
            }
            else
            {
                for(int i=0; i<nj; i++)
                {
                    table[0][i]=K.charAt(i);
                }
            }
            return K;
        }
    }
    public int rest_fill(char[][] table, String key1)
    {
        int l=key1.length();
        int u=0, v=0, temp=0;
        for(int i=0; i<l; i++)
        {
            if(key1.charAt(i)=='i')
            u++;
            else if(key1.charAt(i)=='j')
            v++;
        }
        int ni=0, nj=0;
        ni=l/5;
        nj=l%5;
        int c=65, n=0, x=0;
        if(u>0 && v==0)
        {
            if(ni>0 && nj>0)
            {
                for(int i=nj;i<5;i++)
                {
                    while(table[ni][i]==' ')
                    {
                        for(int j=0; j<l; j++)
                        {
                            if((char)c==key1.charAt(j) || (char)c=='J')
                            x++;
                        }
                        if(x==0)
                        {
                            table[ni][i]=(char)c;
                            c++;
                        }
                        x=0;
                    }
                }
                x=0;
                for(int i=ni+1; i<5; i++)
                {
                    for(int j=0; j<5; j++)
                    {
                        while(table[i][j]==' ')
                        {
                            for(int k=0; k<l; k++)
                            {
                                if((char)c==key1.charAt(j) || (char)c=='J')
                                x++;
                            }
                            if(x==0)
                            {
                                table[i][j]=(char)c;
                                c++;
                            }
                            x=0;
                        }
                    }
                }
            }
            else if(ni==0 && nj>0)
            {
                for(int i=nj; i<5; i++)
                {
                    while(table[0][i]==' ')
                    {
                        for(int j=0;j<l;j++)
                        {
                            if((char)c==key1.charAt(j) || (char)c=='J');
                            x++;
                        }
                        if(x==0)
                        {
                            table[0][i]=(char)c;
                            c++;
                        }
                        x=0;
                    }
                }
                x=0;
                for(int i=1;i<5;i++)
                {
                    for(int j=0; j<5; j++)
                    {
                        while(table[i][j]==' ')
                        {
                            for(int k=0; k<l; k++)
                            {
                                if((char)c==key1.charAt(i) || (char)c=='J')
                                x++;
                            }
                            if(x==0)
                            {
                                table[i][j]=(char)c;
                                c++;
                            }
                            x=0;
                        }
                    }
                }
            }
            else if(ni>0 && nj==0)
            {
                for(int i=ni; i<5;i++)
                {
                    for(int j=0; j<5;j++)
                    {
                        while(table[i][j]==' ')
                        {
                            for(int k=0; k<l;k++)
                            {
                                if((char)c==key1.charAt(i) || (char)c=='J')
                                x++;
                            }
                            if(x==0)
                            {
                                table[i][j]=(char)c;
                                c++;
                            }
                            x=0;
                        }
                    }
                }
            }
            temp=2;
        }
        else if(u==0 && v>0)
        {
            if(ni>0 && nj>0)
            {
                for(int i=nj;i<5;i++)
                {
                    while(table[ni][i]==' ')
                    {
                        for(int j=0; j<l; j++)
                        {
                            if((char)c==key1.charAt(j) || (char)c=='I')
                            x++;
                        }
                        if(x==0)
                        {
                            table[ni][i]=(char)c;
                            c++;
                        }
                        x=0;
                    }
                }
                x=0;
                for(int i=ni+1; i<5; i++)
                {
                    for(int j=0; j<5; j++)
                    {
                        while(table[i][j]==' ')
                        {
                            for(int k=0; k<l; k++)
                            {
                                if((char)c==key1.charAt(j) || (char)c=='I')
                                x++;
                            }
                            if(x==0)
                            {
                                table[i][j]=(char)c;
                                c++;
                            }
                            x=0;
                        }
                    }
                }
            }
            else if(ni==0 && nj>0)
            {
                for(int i=nj; i<5; i++)
                {
                    while(table[0][i]==' ')
                    {
                        for(int j=0;j<l;j++)
                        {
                            if((char)c==key1.charAt(j) || (char)c=='I');
                            x++;
                        }
                        if(x==0)
                        {
                            table[0][i]=(char)c;
                            c++;
                        }
                        x=0;
                    }
                }
                x=0;
                for(int i=1;i<5;i++)
                {
                    for(int j=0; j<5; j++)
                    {
                        while(table[i][j]==' ')
                        {
                            for(int k=0; k<l; k++)
                            {
                                if((char)c==key1.charAt(i) || (char)c=='I')
                                x++;
                            }
                            if(x==0)
                            {
                                table[i][j]=(char)c;
                                c++;
                            }
                            x=0;
                        }
                    }
                }
            }
            else if(ni>0 && nj==0)
            {
                for(int i=ni; i<5;i++)
                {
                    for(int j=0; j<5;j++)
                    {
                        while(table[i][j]==' ')
                        {
                            for(int k=0; k<l;k++)
                            {
                                if((char)c==key1.charAt(i) || (char)c=='I')
                                x++;
                            }
                            if(x==0)
                            {
                                table[i][j]=(char)c;
                                c++;
                            }
                            x=0;
                        }
                    }
                }
            }
            temp=1;
        }
        else if(u==0 && v==0)
        {
            if(ni>0 && nj>0)
            {
                for(int i=nj;i<5;i++)
                {
                    while(table[ni][i]==' ')
                    {
                        for(int j=0; j<l; j++)
                        {
                            if((char)c==key1.charAt(j) || (char)c=='J')
                            {
                                x++;
                                break;
                            }
                        }
                        if(x==0)
                        {
                            table[ni][i]=(char)c;
                        }
                        c++;
                        x=0;    
                    }
                }
                x=0;
                for(int i=ni+1; i<5; i++)
                {
                    for(int j=0; j<5; j++)
                    {
                        while(table[i][j]==' ')
                        {
                            for(int k=0; k<l; k++)
                            {
                                if((char)c==key1.charAt(k) || (char)c=='J')
                                x++;
                            }
                            if(x==0)
                            {
                                table[i][j]=(char)c;
                            }
                            c++;
                            x=0;
                        }
                    }
                }
            }
            else if(ni==0 && nj>0)
            {
                for(int i=nj; i<5; i++)
                {
                    while(table[0][i]==' ')
                    {
                        for(int j=0;j<l;j++)
                        {
                            if((char)c==key1.charAt(j) || (char)c=='J');
                            x++;
                        }
                        if(x==0)
                        {
                            table[0][i]=(char)c;
                            c++;
                        }
                        x=0;
                    }
                }
                x=0;
                for(int i=1;i<5;i++)
                {
                    for(int j=0; j<5; j++)
                    {
                        while(table[i][j]==' ')
                        {
                            for(int k=0; k<l; k++)
                            {
                                if((char)c==key1.charAt(i) || (char)c=='J')
                                x++;
                            }
                            if(x==0)
                            {
                                table[i][j]=(char)c;
                                c++;
                            }
                            x=0;
                        }
                    }
                }
            }
            else if(ni>0 && nj==0)
            {
                for(int i=ni; i<5;i++)
                {
                    for(int j=0; j<5;j++)
                    {
                        while(table[i][j]==' ')
                        {
                            for(int k=0; k<l;k++)
                            {
                                if((char)c==key1.charAt(i) || (char)c=='J')
                                x++;
                            }
                            if(x==0)
                            {
                                table[i][j]=(char)c;
                                c++;
                            }
                            x=0;
                        }
                    }
                }
            }
            temp=2;
            for(int i=0; i<5;i++)
            {
                for(int j=0; j<5; j++)
                {
                    System.out.print(table[i][j]+" ");
                }
                System.out.println();
            }
        }
        return temp;
    }
    public void gen_cyptxt(String ptxt, char[][] table, int temp)
    {
        int l=ptxt.length();
        String ptxt1="";
        int ia1=0, ja1=0, ia2=0, ja2=0;
        for(int i=0; i<l; i++)
        {
            if(ptxt.charAt(i)!=' ')
            ptxt1=ptxt1+ptxt.charAt(i);
        }
        ptxt=ptxt1;
        l=ptxt.length();
        if(l%2!=0)
        {
            ptxt=ptxt+"x";
            l=ptxt.length();
        }
        ptxt=ptxt.toUpperCase();
        String[] bits= new String[l/2];
        String[] bitsn=new String[l/2];
        for (int i = 0; i < l/2; i++)
        {
            bitsn[i]="";
        }
        int j=0, k=2;
        for (int i = 0; i < l/2; i++)
        {
            bits[i]=ptxt.substring(j,k);
            j+=2;
            k+=2;
        }
        boolean hash=true;
        int count=0, ni=0, lim;
        while(hash)
        {
            lim=l/2;
            for(int i=0; i<lim; i++)
            {
                if(bits[i].charAt(0)==bits[i].charAt(1))
                count++;
            }
            if(count==0)
            hash=false;
            else
            {
                for(int i=0; i<lim; i++)
                {
                    if(bits[i].charAt(0)==bits[i].charAt(1))
                    {
                        ni=i;
                        break;
                    }
                }
                ptxt=ptxt.substring(0,(ni*2)-1)+"X"+ptxt.substring((ni*2)-1);
                l=ptxt.length();
                if(l%2!=0)
                {
                    ptxt=ptxt+"X";
                    l=ptxt.length();
                }
                j=0;
                k=0;
                for (int i = 0; i < l/2; i++)
                {
                    bits[i]=ptxt.substring(j,k);
                    j+=2;
                    k+=2;
                }
                hash=true;
            }
            count=0;
        }
        ni=0;
        lim=l/2;
        for(int i=0; i<lim; i++)
        {
            if(bits[i].charAt(0)==bits[i].charAt(1))
            {
                if(bits[i].charAt(0)=='X')
                {
                    lim=i;
                    break;
                }
            }
        }
        if(temp==1)
        {
             for (int i = 0; i < lim; i++)
             {
                 char ch1=bits[i].charAt(0);
                 char ch2=bits[i].charAt(1);
                 if(ch1=='i')
                 ch1='j';
                 else if(ch2=='i')
                 ch2='j';
                 a : for(j=0; j<5; j++)
                 {
                     for(k=0; k<5; k++)
                     {
                         if(table[j][k]==ch1)
                         {
                             ia1=j;
                             ja1=k;
                         }
                         else if(table[j][k]==ch2)
                         {
                             ia2=j;
                             ja2=k;
                         }
                         if((ia1==ia2) && (ja1!=ja2))
                         {
                             if(ja1!=4)
                             bitsn[i]=bitsn[i]+table[j][k+1];
                             else
                             bitsn[i]=bitsn[i]+table[j][0];
                             if(ja2!=4)
                             bitsn[i]=bitsn[i]+table[j][k+1];
                             else
                             bitsn[i]=bitsn[i]+table[j][0];
                             break a;
                         }
                         else if((ia1!=ia2) && (ja1==ja2))
                         {
                             if(ia1!=4)
                             bitsn[i]=bitsn[i]+table[j-1][k];
                             else
                             bitsn[i]=bitsn[i]+table[0][k];
                             if(ia2!=4)
                             bitsn[i]=bitsn[i]+table[j-1][k];
                             else
                             bitsn[i]=bitsn[i]+table[0][k];
                             break a;
                         }
                         else if((ia1!=ia2) && (ja1!=ja2))
                         {
                             bitsn[i]=bitsn[i]+table[j][ja2];
                             bitsn[i]=bitsn[i]+table[j][ja1];
                             break a;
                         }    
                     }
                 }
                 ia1=0;
                 ja1=0;
                 ia2=0;
                 ja2=0;
             }  
        }
        else if(temp==2)
        {
            for (int i = 0; i < lim; i++)
             {
                 char ch1=bits[i].charAt(0);
                 char ch2=bits[i].charAt(1);
                 if(ch1=='j')
                 ch1='i';
                 else if(ch2=='j')
                 ch2='i';
                 a : for(j=0; j<5; j++)
                 {
                     for(k=0; k<5; k++)
                     {
                         if(table[j][k]==ch1)
                         {
                             ia1=j;
                             ja1=k;
                         }
                         else if(table[j][k]==ch2)
                         {
                             ia2=j;
                             ja2=k;
                         }
                         if((ia1==ia2) && (ja1!=ja2))
                         {
                             if(ja1!=4)
                             bitsn[i]=bitsn[i]+table[j][k+1];
                             else
                             bitsn[i]=bitsn[i]+table[j][0];
                             if(ja2!=4)
                             bitsn[i]=bitsn[i]+table[j][k+1];
                             else
                             bitsn[i]=bitsn[i]+table[j][0];
                             break a;
                         }
                         else if((ia1!=ia2) && (ja1==ja2))
                         {
                             if(ia1!=4)
                             bitsn[i]=bitsn[i]+table[j-1][k];
                             else
                             bitsn[i]=bitsn[i]+table[0][k];
                             if(ia2!=4)
                             bitsn[i]=bitsn[i]+table[j-1][k];
                             else
                             bitsn[i]=bitsn[i]+table[0][k];
                             break a;
                         }
                         else if((ia1!=ia2) && (ja1!=ja2))
                         {
                             bitsn[i]=bitsn[i]+table[j][ja2];
                             bitsn[i]=bitsn[i]+table[j][ja1];
                             break a;
                         }    
                     }
                 }
                 ia1=0;
                 ja1=0;
                 ia2=0;
                 ja2=0;
             }
        }
        String cip_txt="";
        for(int i=0; i<lim; i++)
        {
            cip_txt=cip_txt+bitsn[i];
        }
        System.out.println("The corresponding ciphertext is : "+cip_txt);
    }
}