import java.io.*;
class Playfair_Cipher_Remake
{
  public static void main(String args[])throws IOException
  {
    Playfair_Cipher_Remake pf = new Playfair_Cipher_Remake();
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    System.out.print("Enter the message: ");
    String pt = br.readLine();

    System.out.print("Enter the key: ");
    String k = br.readLine(); //Taking the key input

    char[][] matrix = new char[5][5];
    for(int i=0;i<5;i++)
    {
      for(int j=0;j<5;j++)
      {
        matrix[i][j] = ' ';
      }
    }

    String key = pf.final_key(k); //This is to modify the key
    pf.fill_key(key, matrix); //This is to fill the key into the matrix
    pf.fill_rest(key, matrix);//This is to fill rest of the letters into the matrix
    pf.display_matrix(matrix);//This is to display the matrix
    String modified_text = pf.modified_plain_text(pt);//This is to modify the entired plain text
    String encrypted_text = pf.generate_encrypted_text(modified_text, matrix);//
    // System.out.println("Key: "+key);
    // System.out.println("Modified Plain Text: "+modified_text);
    System.out.println("Encrypted Text: "+encrypted_text);
  }

  public String final_key(String k)
  {
    k = k.toUpperCase();

    int len = k.length();
    int count = 0;

    String key = "";

    int temp;
    while(k.indexOf('J') >= 0 )
    {
        temp = k.indexOf('J');
        k = k.substring(0,temp)+'I'+k.substring(temp+1);
    }

    for(int i=0;i<len;i++)
    {
      if(key.indexOf(k.charAt(i)) < 0)
        key = key + k.charAt(i);
    }

    return key;
  }

  public void fill_key(String key, char[][] matrix)
  {
    int len, row_needed, column_needed, count;
    count = 0;
    len = key.length();
    row_needed = len/5;
    column_needed = len%5; //Number of extra column needed besides totally fille throws
    for(int i=0; i<row_needed; i++)
    {
      for(int j=0; j<5; j++)
      {
        matrix[i][j] = key.charAt(count);
        count += 1;
      }
    }
    for(int j=0; j<column_needed; j++)
    {
      matrix[row_needed][j] = key.charAt(count);
      count += 1;
    }
  }

  public void fill_rest(String key, char[][] matrix)
  {
    char ch = 'A';
    int i=0;
    int j=0;
    while(ch<='Z')
    {
      if(key.indexOf(ch)<0 && ch!= 'J')
      {
        if(matrix[i][j] == ' ')
        {
          matrix[i][j] = ch;
          ch++;
        }

        if(j==4)
          i = (i+1)%5;
        j = (j+1)%5;
      }
      else
        ch++;
    }
  }

  public void display_matrix(char[][] matrix)
  {
    for(int i=0; i<5; i++)
    {
      for(int j=0; j<5; j++)
      {
        System.out.print(matrix[i][j]);
      }
      System.out.println();
    }
  }

  public String modified_plain_text(String plain_text)
  {
    String modified_text = "";
    int len = plain_text.length();
    int temp;

    plain_text = plain_text.toUpperCase();

    //Removig blank spaces
    while(plain_text.indexOf(' ') >= 0)
    {
      temp = plain_text.indexOf(' ');
      plain_text = plain_text.substring(0,temp)+plain_text.substring(temp+1);
    }

    //Replacing J with I
    while(plain_text.indexOf('J') >= 0 )
    {
        temp = plain_text.indexOf('J');
        plain_text = plain_text.substring(0,temp)+'I'+plain_text.substring(temp+1);
    }

    //Adding X between two consequitive same letters
    for(int i=0; i<len-1; i+=2)
    {
      if(plain_text.charAt(i) == plain_text.charAt(i+1))
        modified_text = modified_text + plain_text.charAt(i) + "X" + plain_text.charAt(i+1);
      else
        modified_text = modified_text + plain_text.charAt(i) + plain_text.charAt(i+1);
    }

    if(len%2!=0)
      modified_text = modified_text + plain_text.charAt(len-1) + "X";
    modified_text = modified_text.toUpperCase();

    return modified_text;
  }

  public String generate_encrypted_text(String text, char[][] matrix)
  {
    String encrypted_text = "";
    char c1, c2;
    int c1_i, c1_j, c2_i, c2_j;
    c1_i = c1_j = c2_i = c2_j = 0;
    int len;
    len = text.length();
    for(int k=0; k<len; k+=2)
    {
      c1 = text.charAt(k);
      c2 = text.charAt(k+1);

      //Finding the elements in the display_matrix
      for(int i=0; i<5; i++)
      {
        for(int j=0; j<5; j++)
        {
          if(matrix[i][j] == c1)
          {
            c1_i = i;
            c1_j = j;
          }
          if(matrix[i][j] == c2)
          {
            c2_i = i;
            c2_j = j;
          }
        }
      }

      //If elements are in the same row
      if(c1_i==c2_i && c1_j!=c2_j)
        encrypted_text = encrypted_text + matrix[c1_i][(c1_j+1)%5] + matrix[c2_i][(c2_j+1)%5];
      else if(c1_i!=c2_i && c1_j==c2_j)
        encrypted_text = encrypted_text + matrix[(c1_i+1)%5][c1_j] + matrix[(c2_i+1)%5][c2_j];
      else if(c1_i!=c2_i && c1_j!=c2_j)
        encrypted_text = encrypted_text + matrix[c1_i][c2_j] + matrix[c2_i][c1_j];
      else
        encrypted_text = "ERROR_GENERATED";
    }
    return encrypted_text;
  }
}
