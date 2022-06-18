import { Link } from "react-router-dom";

const StartHeader = () => {
  return (
    <nav className="navbar navbar-expand-lg navbar-light">
      <div className="container">
        <Link className="navbar-brand" to={"/sign-in"}>PersonalFinanceApp</Link>
        <div className="collapse navbar-collapse">
          <ul className="navbar-nav ml-auto">
            <li className="nav-item">
              <Link className="nav-link" to={"/sign-in"}>Login</Link>
            </li>
            <li className="nav-item">
              <Link className="nav-link" to={"/sign-up"}>Register</Link>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  )
}

export default StartHeader