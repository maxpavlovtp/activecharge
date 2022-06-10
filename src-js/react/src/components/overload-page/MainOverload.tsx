import Header from "../../components/header/Header";
import styles from '../../App/mainScreen/Main.module.css'
import Footer from "../../components/footer/Footer";
import OverloadPage from "./OverloadPage";

function HomeScreen() {
  return (
    <div className={styles.mainContainer}>
      <Header/>
      <OverloadPage />
      <Footer/>
    </div>

  );
}

export default HomeScreen;
