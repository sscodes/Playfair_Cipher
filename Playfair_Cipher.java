import java.io.*;    
class Playfair_Cipher
{
    public static void main(String args[])throws IOException
    {
        Playfair_Cipher p=new Playfair_Cipher();
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in)); 
        
        System.out.print("Enter the plaintext : ");
        String pt=br.readLine();    //pt -> plain text. It's the text that's to be converted.
        
        System.out.print("Enter the key : ");
        String key=br.readLine();   //key -> the key to the cipher
        
        char[][] T = new char[5][5];    //the cipher matrix. T -> table
        
        //initializing all the positions of the matrix as ' '(space).
        for(int i=0; i<5; i++)  
        {
            for(int j=0; j<5; j++)
            {
                T[i][j]=' ';
            }
        }
        
        //calling the function key_fill with the table and key as parameters and the transformed key is stored in a
        String a=p.key_fill(key, T);    
        
        //calling the function rest_fill with the table and the transformed key as parameters and the type of matrix is stored in var
        //if var = 1 it contains j if var = 2 it contains i
        int var=p.rest_fill(T, a);  
        
        //calling the function gen_cyptxt with the plain text, table and type of matrix as parameters
        p.gen_cyptxt(pt, T, var);   
    }
    
    //function to fill out the keys in the table
    public String key_fill(String K, char[][] table)
    {
        int c=0, x=0, ni=0, nj=0, z=0;
        K=K.toUpperCase(); //converting the whole key to uppercase
        int l=K.length();
        String key1="";
        
        //checking whether any letter is present more than once or not.
        for(int i=0; i<l-1; i++)    
        {
            for(int j=i+1; j<l; j++)
            {
                if(K.charAt(i)==K.charAt(j))
                {
                    c++;    //c -> count. 
                    break;  //If any letter is present more than once c becomes 1 & the loops are terminated.
                }
            }
        }
        
        //If any letter is present more than once execute this loop.
        if(c==1)    
        {
            int l1=key1.length();
            
            //removing the extra letters
            for(int i =0; i<l; i++)
            {
                l1=key1.length();
                for(int j=0; j<l1; j++)
                {
                    if(K.charAt(i)==key1.charAt(j))
                    x++;
                }
                if(x==0)
                key1=key1+K.charAt(i);
                x=0;
            }
            
            int len=key1.length();
            int u=0, v=0;
            
            //checking the no. of I & J
            for(int i=0; i<len; i++)
            {
                if(key1.charAt(i)=='I')
                u++;
                else if(key1.charAt(i)=='J')
                v++;
            }
            
            //if i & j both are present, transform it to a "only i present key"
            if(u>0 && v>0)
            {
                String str="";
                
                //if j is encountered in the key, ignore it. Take only the i.
                for(int i=0; i<len; i++)
                {
                    if(key1.charAt(i)!='J')
                    str=str+key1.charAt(i); //creating the new key with no J in it
                    else
                    continue;
                }
                key1=str;   //storing the new key to key1
            }
            len=key1.length();
            
            ni=len/5;   //checking the no. of rows the key requires
            nj=len%5;   //checking the no. of columns (other than the full rows) the key requires
            
            //if the key requires more than or equal to 1 row
            if(ni>0)
            {
                //filling the complete rows first
                for(int i=0;i<ni;i++)
                {
                    for(int j=0; j<5; j++)
                    {
                        table[i][j]=key1.charAt(z);
                        z++;
                    }
                }
                
                //then filling the independent columns
                for(int i=0; i<nj; i++)
                {
                    table[ni][nj]=key1.charAt(z);
                    z++;
                }
            }
            
            //if the key requires less than 1 row then execute this
            else
            {
                for(int i=0; i<nj; i++)
                {
                    table[0][i]=key1.charAt(i);
                }
            }
            return key1;    //after the table is filled, the transformed key is sent to main
        }
        
        
        //if no letter in the key is reapeated.
        else 
        {
            int u=0, v=0;
            
            //counting the no. of i and j
            for(int i=0; i<l; i++)
            {
                if(K.charAt(i)=='I')
                u++;
                else if(K.charAt(i)=='J')
                v++;
            }
            
            //if i & j both are present, transform it to a "only i present key"
            if(u>0 && v>0)
            {
                String str="";
                
                //if j is encountered in the key, ignore it. Take only the i.
                for(int i=0; i<l; i++)
                {
                    if(K.charAt(i)!='J')
                    str=str+K.charAt(i); //creating the new key with no J in it
                    else
                    continue;
                }
                K=str; //storing the new key to K
            }
            l=K.length();
            
            ni=l/5; //checking the no. of rows the key requires
            nj=l%5; //checking the no. of columns (other than the full rows) the key requires
            
            
            //if the key requires more than or equal to 1 row
            if(ni>0)
            {
                //filling the complete rows first
                for(int i=0; i<ni; i++)
                {
                    for(int j=0;j<5;j++)
                    {
                        table[i][j]=K.charAt(z);
                        z++;
                    }
                }
                
                //then filling the independent columns
                for(int i=0; i<nj; i++)
                {
                    table[ni][i]=K.charAt(z);
                    z++;
                }
            }
            
            
            //if the key requires less than 1 row then execute this
            else
            {
                for(int i=0; i<nj; i++)
                {
                    table[0][i]=K.charAt(i);
                }
            }
            
            return K; //after the table is filled, the transformed key is sent to main 
        }
    }
    
    //function to fill out the rest of the table
    public int rest_fill(char[][] table, String key1)
    {
        int l=key1.length();
        int u=0, v=0, temp=0;
        
        //counting the no. of I & J
        for(int i=0; i<l; i++)
        {
            if(key1.charAt(i)=='I')
            u++;
            else if(key1.charAt(i)=='J')
            v++;
        }
        
        
        int ni=0, nj=0;
        
        ni=l/5; //checking the no. of rows the key requires
        nj=l%5; //checking the no. of columns (other than the full rows) the key requires
        int c=65, n=0, x=0;
        
        //if key has i but no j
        if(u>0 && v==0)
        {
            //if the key requires more than or equal to 1 row and also it has some independent columns
            if(ni>0 && nj>0)
            {
                //starting to fill-up after the independent columns end
                for(int i=nj;i<5;i++)
                {
                    //checking if the character is present in the key or if it's J
                    for(int j=0; j<l; j++)
                    {
                        if((char)c==key1.charAt(j) || (char)c=='J')
                        x++;
                    }
                    
                    //if the character is present in the key or if it's J ignore it else add to matrix
                    if(x==0)
                    {
                        table[ni][i]=(char)c;
                        c++;
                    }
                    x=0;
                }
                x=0;
                
                //start filling out rest of the rows
                for(int i=ni+1; i<5; i++)
                {
                    for(int j=0; j<5; j++)
                    {
                        //checking if the character is present in the key or if it's J
                        for(int k=0; k<l; k++)
                        {
                            if((char)c==key1.charAt(j) || (char)c=='J')
                            x++;
                        }
                        
                        //if the character is present in the key or if it's J ignore it else add to matrix
                        if(x==0)
                        {
                            table[i][j]=(char)c;
                            c++;
                        }
                        x=0;
                    }
                }
            }
            
            //if key gets completed in one row without using all the columns
            else if(ni==0 && nj>0)
            {
                //start filling out the rest of the columns in the first row
                for(int i=nj; i<5; i++)
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
                x=0;
                
                //filing the rest of the rows
                for(int i=1;i<5;i++)
                {
                    for(int j=0; j<5; j++)
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
            
            //if key takes full rows but needs no independent columns
            else if(ni>0 && nj==0)
            {
                for(int i=ni; i<5;i++)
                {
                    for(int j=0; j<5;j++)
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
            temp=2;
        }
        
        //if key has no i but only j
        else if(u==0 && v>0)
        {
            //if key requires some complete rows and few independent columns
            if(ni>0 && nj>0)
            {
                //starting to fill-up after the independent columns end
                for(int i=nj;i<5;i++)
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
                x=0;
                
                //filling up the remaining rows
                for(int i=ni+1; i<5; i++)
                {
                    for(int j=0; j<5; j++)
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
            
            //if key gets completed within 1 row without all 5 columns
            else if(ni==0 && nj>0)
            {
                //filling out the rest columns in row1
                for(int i=nj; i<5; i++)
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
                x=0;
                
                //filling out the rest of the rows
                for(int i=1;i<5;i++)
                {
                    for(int j=0; j<5; j++)
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
            
            //if key gets completed in whole rows without any independent columns
            else if(ni>0 && nj==0)
            {
                
                //filling out the rest of the rows
                for(int i=ni; i<5;i++)
                {
                    for(int j=0; j<5;j++)
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
            temp=1;
        }
        
        //if the key doesn't have i and j
        else if(u==0 && v==0)
        {
            //if key requires whole rows and few independent columns
            if(ni>0 && nj>0)
            {
                //filling out the rest of the columns
                for(int i=nj;i<5;i++)
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
                x=0;
                
                //filling out the rest of the rows
                for(int i=ni+1; i<5; i++)
                {
                    for(int j=0; j<5; j++)
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
            
            //if the key gets over within few columns and no complete ros are required
            else if(ni==0 && nj>0)
            {
                //filling out the incomplete row
                for(int i=nj; i<5; i++)
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
                x=0;
                
                //filling out the rest of the rows
                for(int i=1;i<5;i++)
                {
                    for(int j=0; j<5; j++)
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
            
            //if the key requires only rows and no independent columns
            else if(ni>0 && nj==0)
            {
                //filling out the rest of the columns
                for(int i=ni; i<5;i++)
                {
                    for(int j=0; j<5;j++)
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
            temp=2;
        }
        
        //printing the table
        for(int i=0; i<5;i++)
        {
            for(int j=0; j<5; j++)
            {
                System.out.print(table[i][j]+" ");
            }
            System.out.println();
        }
        return temp;    //if temp = 2 i is in matrix and j is ignored. if temp = 1 j is in matrix and i is ignored
    }
    
    //function to generate the cypher text
    public void gen_cyptxt(String ptxt, char[][] table, int temp)
    {
        int l=ptxt.length();
        String ptxt1="";
        int ia1=0, ja1=0, ia2=0, ja2=0;
        
        //removing all the inner spaces of plaint text
        for(int i=0; i<l; i++)
        {
            if(ptxt.charAt(i)!=' ')
            ptxt1=ptxt1+ptxt.charAt(i); // storing it in ptxt1
        }
        
        ptxt=ptxt1; //storing it back in ptxt
        l=ptxt.length();
        
        //adding a dummy letter 'x' if not length is even
        if(l%2!=0)
        {
            ptxt=ptxt+"x";
            l=ptxt.length();
        }
        
        ptxt=ptxt.toUpperCase();    //converting the whole plain text to upper case
        
        //creating array of string 'bits' of length half of plain text
        String[] bits= new String[l/2];
        String[] bitsn=new String[l/2];
        
        //initialising the array with ""
        for (int i = 0; i < l/2; i++)
        {
            bitsn[i]="";
        }
        int j=0, k=2;
        
        //storing each 2 letter bit of plain text into the array of strings
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
            //checking if two double letters are in same bit
            for(int i=0; i<lim; i++)
            {
                if(bits[i].charAt(0)==bits[i].charAt(1))
                count++;
            }
            
            //if not then loop is terminated
            if(count==0)
            hash=false;
            
            //else dummy character is added in between
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
        
        //checking if 'XX' is bit comes at the end. If comes the limit is taken before that position
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
        
        //if matrix is of type where J is taken
        if(temp==1)
        {
             for (int i = 0; i < lim; i++)
             {
                 
                 //storing the 1st letter of the bit in ch1 & 2nd letter in ch2
                 char ch1=bits[i].charAt(0);
                 char ch2=bits[i].charAt(1);
                 
                 //since the matrix contains J and I & J have same position so I is converted to J
                 if(ch1=='I')
                 ch1='J';
                 else if(ch2=='I')
                 ch2='J';
                 
                 
                 a : for(j=0; j<5; j++) //loop labelled as a
                 {
                     for(k=0; k<5; k++)
                     {
                         
                         //storing the cordinates of the 1st letter
                         if(table[j][k]==ch1)
                         {
                             ia1=j;
                             ja1=k;
                         }
                         
                         //storing the cordinates of the 2nd letter
                         else if(table[j][k]==ch2)
                         {
                             ia2=j;
                             ja2=k;
                         }
                         
                         //if they are in the same row
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
                         
                         //if they are in the same column
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
                         
                         //if they are neither in same row nor in same column
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
        
        //if matrix is of type where I is taken
        else if(temp==2)
        {
            for (int i = 0; i < lim; i++)
             {
                 
                 //storing the 1st letter of the bit in ch1 & 2nd letter in ch2
                 char ch1=bits[i].charAt(0);
                 char ch2=bits[i].charAt(1);
                 
                 //since the matrix contains I and I & J have same position so J is converted to I
                 if(ch1=='j')
                 ch1='i';
                 else if(ch2=='j')
                 ch2='i';
                 
                 
                 a : for(j=0; j<5; j++) //loop labelled as a
                 {
                     for(k=0; k<5; k++)
                     {
                         
                         //storing the cordinates of the 1st letter
                         if(table[j][k]==ch1)
                         {
                             ia1=j;
                             ja1=k;
                         }
                         
                         //storing the cordinates of the 2nd letter
                         else if(table[j][k]==ch2)
                         {
                             ia2=j;
                             ja2=k;
                         }
                         
                         //if they are in the same row
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
                         
                         //if they are in the same column
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
                         
                         //if they are neither in same row nor in same column
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
        
        //adding up all the cypher bits to form the cypher text
        for(int i=0; i<lim; i++)
        {
            cip_txt=cip_txt+bitsn[i];
        }
        
        //finally pinting the cypher text
        System.out.println("The corresponding ciphertext is : "+cip_txt);
    }
}
