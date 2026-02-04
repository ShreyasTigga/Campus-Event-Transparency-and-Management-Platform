export default function StudentDashboard() {
  return (
    <div className="p-8">
      <h1 className="text-4xl font-bold mb-6">🎓 Student Dashboard</h1>

      <div className="grid md:grid-cols-2 gap-6">
        <Card
          title="Available Events"
          desc="Browse and enroll in approved campus events"
        />
        <Card
          title="My Enrollments"
          desc="View events you’ve registered for"
        />
      </div>
    </div>
  );
}

function Card({ title, desc }: { title: string; desc: string }) {
  return (
    <div className="bg-white/10 backdrop-blur-lg p-6 rounded-xl border border-white/10 hover:border-blue-500 transition">
      <h2 className="text-xl font-semibold mb-2">{title}</h2>
      <p className="text-white/70">{desc}</p>
    </div>
  );
}
