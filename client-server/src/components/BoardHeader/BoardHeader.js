import { Link } from "react-router-dom";

const BoardHeader = ({ onLogout }) => {

  return (
    <nav className="navbar navbar-expand-lg navbar-light">
      <div className="container">
        <Link className="navbar-brand" to={"/app"}>PersonalFinanceApp</Link>
        <div className="collapse navbar-collapse">
          <ul className="navbar-nav ml-auto">
            <li className="nav-item">
              <Link className="nav-link" to={"/app/board"}>Board</Link>
            </li>
            <li className="nav-item">
              <Link className="nav-link" to={"/app/savings"}>Savings</Link>
            </li>
            <li className="nav-item">
              <Link className="nav-link" to={"/app/expenses"}>Expenses</Link>
            </li>
            <li className="nav-item">
              <Link className="nav-link" to={"/app/goals"}>Goals</Link>
            </li>
            <li className="nav-item">
              <a className="nav-link" style={{ cursor: 'pointer' }} onClick={onLogout} >Logout</a>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  )
}

export default BoardHeader