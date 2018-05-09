package ArneRemaster;

public class Vertex {

	 private String name;
     private boolean mark;

     public Vertex(String name)
     {
         mark = false;
         this.name = name;
     }

     public String toString()//For debugging purposes
     {
         return name + "\t" + mark;
     }

     public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public boolean isMark() {
			return mark;
		}

		public void setMark(boolean mark) {
			this.mark = mark;
		}

		public boolean equals(Object obj)
     {
         Vertex other = (Vertex)obj;
         return name.equals(other.getName());
     }

     public  int getHashCode()
     {
         return name.hashCode();
     }	     


}
