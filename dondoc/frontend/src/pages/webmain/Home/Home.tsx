import "./Home.module.css";
import Nav from "../../Nav";
interface Props {

}

function Home(props: Props) {
  return (
    <div style={{height:"100vh"}}>
      안녕하세요
      <Nav/>
    </div>
  );
};

export default Home;
